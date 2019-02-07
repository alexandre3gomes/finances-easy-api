package net.finance.auth;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.google.common.net.HttpHeaders;

import lombok.experimental.FieldDefaults;

@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final String BEARER = "Bearer";

	public TokenAuthenticationFilter(final RequestMatcher requiresAuth) {
		super(requiresAuth);
	}

	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) {
		final String param = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.orElse(request.getParameter("t"));
		final String token = Optional.ofNullable(param)
				.map(value -> StringUtils.removeStart(value, TokenAuthenticationFilter.BEARER)).map(String::trim)
				.orElseThrow(() -> new BadCredentialsException("Missing Authentication Token"));
		final Authentication auth = new UsernamePasswordAuthenticationToken(token, token);
		return getAuthenticationManager().authenticate(auth);
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

}
