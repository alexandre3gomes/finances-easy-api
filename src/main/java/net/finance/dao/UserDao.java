package net.finance.dao;

import java.util.Optional;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import net.finance.entity.User;

@Repository
public class UserDao extends GenericDaoImpl<User> {

	@SuppressWarnings("unchecked")
	public Optional<User> getUserByUsernameAndPass(final String username, final String password) {
		final String sql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
		final Query q = em.createQuery(sql);
		q.setParameter("username", username);
		q.setParameter("password", password);
		return q.getResultList().stream().findFirst();
	}
}
