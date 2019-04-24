package net.finance.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import net.finance.entity.User;
import net.finance.repository.UserRepository;
import net.finance.utils.EncryptUtils;

@Service
@Transactional
public class UserBo {

	protected Map<String, User> loggedUsers = new HashMap<>();

	@NonNull
	private final UserRepository userRep;

	@Autowired
	public UserBo(final UserRepository userRep) {
		this.userRep = userRep;
	}

	public Optional<User> findByToken(final String token) {
		return Optional.ofNullable(loggedUsers.get(token));
	}

	@Bean
	@Profile("dev")
	public void initTestUser() {
		System.out.println("Creating test user...");
		final Optional<User> alexandre = userRep.getUserByUsernameAndPassword("alexandre",
				EncryptUtils.hashPassword("spfc2408").get());
		User admin = new User();
		if (alexandre.isPresent()) {
			admin = alexandre.get();
		} else {
			admin.setId(1);
			admin.setName("Alexandre");
			admin.setUsername("alexandre");
			admin.setPassword("924d5413f06c0fba1ded3a11f61171ee");
			userRep.save(admin);
		}
		loggedUsers.put("7cd2f9e1-a6e9-4675-9176-b9219b0fd8d8", admin);
	}

	public User create(final User user) {
		user.setPassword(EncryptUtils.hashPassword(user.getPassword()).get());
		return userRep.save(user);
	}

	public Optional<User> login(final String username, final String password) throws NoSuchElementException {
		String token = UUID.randomUUID().toString();
		User user = new User();
		final Optional<User> optDev = userRep.getUserByUsernameAndPassword(username,
				EncryptUtils.hashPassword(password).get());
		if (optDev.isPresent()) {
			user = optDev.get();
			user.setToken(token);
			loggedUsers.put(token, user);
		} else {
			token = null;
		}
		return Optional.of(user);
	}

	public void logout(final User user) {
		String keyToRemove = null;
		for (final Map.Entry<String, User> entry : loggedUsers.entrySet()) {
			if (entry.getValue() == user) {
				keyToRemove = entry.getKey();
				break;
			}
		}
		if (keyToRemove != null) {
			loggedUsers.remove(keyToRemove);
		}
	}
}
