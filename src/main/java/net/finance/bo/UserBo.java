package net.finance.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	public void delete(final Integer id) {
		userRep.deleteById(id);
	}

	public User get(final Integer id) {
		return userRep.findById(id).get();
	}

	public Page<User> list(final PageRequest pageReq) {
		return userRep.findAll(pageReq);
	}

	public User update(final User user) {
		return userRep.save(user);
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
