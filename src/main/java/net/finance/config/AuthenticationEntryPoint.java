package net.finance.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("DeveloperStack");
		super.afterPropertiesSet();
	}

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authEx) throws IOException, ServletException {
		response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		final PrintWriter writer = response.getWriter();
		writer.println("HTTP Status 401 - " + authEx.getMessage());
	}

}