package net.finance.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import net.finance.dao.UserDao;
import net.finance.entity.User;

@Service
@Transactional
public class UserBo implements GenericBo<User> {

	protected Map<String, User> loggedUsers = new HashMap<>();

	@NonNull
	private final UserDao devDao;

	@Autowired
	public UserBo(final UserDao devDao) {
		this.devDao = devDao;
	}

	@Override
	public User create(final User dev) {
		return devDao.create(dev);
	}

	@Override
	public void delete(final Integer id) {
		devDao.delete(id);
	}

	@Override
	public User findById(final Integer id) {
		return devDao.find(id);
	}

	public Optional<User> findByToken(final String token) {
		return Optional.ofNullable(loggedUsers.get(token));
	}

	/**
	 * This method needs to be excluded on production environment
	 */
	@PostConstruct
	public void initTestUser() {
		final User admin = new User();
		admin.setId(0);
		admin.setName("Admin");
		admin.setUsername("admin");
		admin.setPassword("admin");
		loggedUsers.put("7cd2f9e1-a6e9-4675-9176-b9219b0fd8d8", admin);
	}

	@Override
	public List<User> listAll() {
		return devDao.list();
	}

	public Optional<String> login(final String username, final String password) {
		final String token = UUID.randomUUID().toString();
		final Optional<User> optDev = devDao.getUserByUsernameAndPass(username, password);
		if (optDev.isPresent()) {
			final User dev = optDev.get();
			loggedUsers.put(token, dev);
		}
		return Optional.of(token);
	}

	public void logout(final User dev) {
		for (final Map.Entry<String, User> entry : loggedUsers.entrySet()) {
			if (entry.getValue() == dev) {
				loggedUsers.remove(entry.getKey());
			}
		}
	}

	@Override
	public User update(final User dev) {
		return devDao.update(dev);
	}

}
