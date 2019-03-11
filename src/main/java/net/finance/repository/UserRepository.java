package net.finance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import net.finance.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> getUserByUsernameAndPassword(String username, String password);

}