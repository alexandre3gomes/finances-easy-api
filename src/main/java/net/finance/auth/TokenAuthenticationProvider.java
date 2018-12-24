package net.finance.auth;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PUBLIC;

import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import net.finance.bo.UserBo;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;

@Component
@AllArgsConstructor(access = PUBLIC)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@NonNull UserBo auth;

	@Override
	protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
		// Nothing to do
	}

	@Override
	protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
		final Object token = authentication.getCredentials();
		return Optional.ofNullable(token).map(String::valueOf).flatMap(auth::findByToken).orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token=" + token));
	}
}
