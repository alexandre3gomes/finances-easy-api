package net.finance.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.RedirectStrategy;

public class NoRedirectStrategy implements RedirectStrategy {

	@Override
	public void sendRedirect(final HttpServletRequest arg0, final HttpServletResponse arg1, final String arg2) throws IOException {
		// TODO No redirect is required
	}

}
