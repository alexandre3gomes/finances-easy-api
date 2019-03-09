package net.finance.bo;

import java.util.HashMap;
import java.util.List;
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
import net.finance.dao.UserDao;
import net.finance.entity.User;
import net.finance.utils.EncryptUtils;

@Service
@Transactional
public class UserBo implements GenericBo<User> {

	protected Map<String, User> loggedUsers = new HashMap<>();

	@NonNull
	private final UserDao userDao;

	@Autowired
	public UserBo(final UserDao devDao) {
		userDao = devDao;
	}

	@Override
	public User create(final User user) {
		user.setPassword(EncryptUtils.hashPassword(user.getPassword()).get());
		return userDao.create(user);
	}

	@Override
	public void delete(final Integer id) {
		userDao.delete(id);
	}

	@Override
	public User findById(final Integer id) {
		return userDao.find(id);
	}

	public Optional<User> findByToken(final String token) {
		return Optional.ofNullable(loggedUsers.get(token));
	}

	@Bean
	@Profile("dev")
	public void initTestUser() {
		System.out.println("Creating test user...");
		final User admin = new User();
		admin.setId(1);
		admin.setName("Alexandre");
		admin.setUsername("alexandre");
		admin.setPassword("e10adc3949ba59abbe56e057f20f883e");
		loggedUsers.put("7cd2f9e1-a6e9-4675-9176-b9219b0fd8d8", admin);
	}

	@Override
	public List<User> listAll() {
		return userDao.list();
	}

	public Optional<User> login(final String username, final String password) throws NoSuchElementException {
		String token = UUID.randomUUID().toString();
		User user = null;
		final Optional<User> optDev = userDao.getUserByUsernameAndPass(username,
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

	@Override
	public User update(final User user) {
		return userDao.update(user);
	}

}
