package com.finances.config

import com.finances.auth.TokenAuthenticationFilter
import com.finances.auth.TokenAuthenticationProvider
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SpringSecurityConfig(val provider: TokenAuthenticationProvider?) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(provider)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS).and()
                .authenticationProvider(provider)
                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter::class.java).authorizeRequests()
                .anyRequest().authenticated().and().csrf().disable().formLogin().disable().httpBasic().disable()
                .logout().disable()
    }

    @Bean
    fun forbiddenEntryPoint(): AuthenticationEntryPoint {
        return HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)
    }

    @Bean
    @Throws(Exception::class)
    fun restAuthenticationFilter(): TokenAuthenticationFilter {
        val filter = TokenAuthenticationFilter(PROTECTED_URLS)
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(successHandler())
        return filter
    }

    @Bean
    fun successHandler(): SimpleUrlAuthenticationSuccessHandler {
        val successHandler = SimpleUrlAuthenticationSuccessHandler()
        successHandler.setRedirectStrategy(NoRedirectStrategy())
        return successHandler
    }

    override fun configure(web: WebSecurity) {
        val SWAGGER_UI: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/swagger-ui/**"))
        val SWAGGER_API: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/v2/api-docs"))
        val SWAGGER_WEBJARS: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/webjars/**"))
        val SWAGGER_CONFIG: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/configuration/**"))
        val SWAGGER_RESOURCES: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/swagger-resources/**"))
        val ACTUATOR: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/actuator/**"))
        web.ignoring().requestMatchers(PUBLIC_URLS)
                .and().ignoring().requestMatchers(SWAGGER_API)
                .and().ignoring().requestMatchers(SWAGGER_UI)
                .and().ignoring().requestMatchers(SWAGGER_WEBJARS)
                .and().ignoring().requestMatchers(SWAGGER_CONFIG)
                .and().ignoring().requestMatchers(SWAGGER_RESOURCES)
                .and().ignoring().requestMatchers(ACTUATOR)
                .and().ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**")
    }

    /**
     * Disable Spring boot automatic filter registration.
     */
    @Bean
    fun disableAutoRegistration(filter: TokenAuthenticationFilter?): FilterRegistrationBean<*> {
        val registration: FilterRegistrationBean<TokenAuthenticationFilter> = FilterRegistrationBean<TokenAuthenticationFilter>(filter)
        registration.setEnabled(false)
        return registration
    }

    companion object {
        private val PUBLIC_URLS: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/public/**"))
        private val PROTECTED_URLS: RequestMatcher = NegatedRequestMatcher(PUBLIC_URLS)
    }
}