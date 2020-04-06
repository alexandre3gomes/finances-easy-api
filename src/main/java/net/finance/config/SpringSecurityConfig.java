package net.finance.config;

import lombok.experimental.FieldDefaults;
import net.finance.auth.TokenAuthenticationFilter;
import net.finance.auth.TokenAuthenticationProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(new AntPathRequestMatcher("/public/**"));

	private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(SpringSecurityConfig.PUBLIC_URLS);

	TokenAuthenticationProvider provider;

	SpringSecurityConfig(final TokenAuthenticationProvider provider) {
		super();
		this.provider = Objects.requireNonNull(provider);
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(provider);
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(STATELESS).and().exceptionHandling()
				.defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), SpringSecurityConfig.PROTECTED_URLS).and()
				.authenticationProvider(provider)
				.addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter.class).authorizeRequests()
				.anyRequest().authenticated().and().csrf().disable().formLogin().disable().httpBasic().disable()
				.logout().disable();
	}

	@Override
	public void configure(final WebSecurity web) {
		RequestMatcher SWAGGER_UI = new OrRequestMatcher(new AntPathRequestMatcher("/swagger-ui.html/**"));
		RequestMatcher SWAGGER_API = new OrRequestMatcher(new AntPathRequestMatcher("/v2/api-docs"));
		RequestMatcher SWAGGER_WEBJARS = new OrRequestMatcher(new AntPathRequestMatcher("/webjars/**"));
		RequestMatcher SWAGGER_CONFIG = new OrRequestMatcher(new AntPathRequestMatcher("/configuration/**"));
		RequestMatcher SWAGGER_RESOURCES = new OrRequestMatcher(new AntPathRequestMatcher("/swagger-resources/**"));
		web.ignoring().requestMatchers(SpringSecurityConfig.PUBLIC_URLS)
				.and().ignoring().requestMatchers(SWAGGER_API)
				.and().ignoring().requestMatchers(SWAGGER_UI)
				.and().ignoring().requestMatchers(SWAGGER_WEBJARS)
				.and().ignoring().requestMatchers(SWAGGER_CONFIG)
				.and().ignoring().requestMatchers(SWAGGER_RESOURCES)
				.and().ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
	}

	/**
	 * Disable Spring boot automatic filter registration.
	 */
	@Bean
	public FilterRegistrationBean<?> disableAutoRegistration(final TokenAuthenticationFilter filter) {
		final FilterRegistrationBean<TokenAuthenticationFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setEnabled(false);
		return registration;
	}

	@Bean
	public AuthenticationEntryPoint forbiddenEntryPoint() {
		return new HttpStatusEntryPoint(UNAUTHORIZED);
	}

	@Bean
	public TokenAuthenticationFilter restAuthenticationFilter() throws Exception {
		final TokenAuthenticationFilter filter = new TokenAuthenticationFilter(SpringSecurityConfig.PROTECTED_URLS);
		filter.setAuthenticationManager(authenticationManager());
		filter.setAuthenticationSuccessHandler(successHandler());
		return filter;
	}

	@Bean
	public SimpleUrlAuthenticationSuccessHandler successHandler() {
		final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setRedirectStrategy(new NoRedirectStrategy());
		return successHandler;
	}
}
