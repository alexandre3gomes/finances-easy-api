package net.finance.bo;

import lombok.NonNull;
import net.finance.entity.User;
import net.finance.repository.UserRepository;
import net.finance.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserBo {

    @NonNull
    private final UserRepository userRep;
    private Map<String, User> loggedUsers = new HashMap<>();

    @Autowired
    public UserBo(UserRepository userRep) {
        this.userRep = userRep;
    }

    public Optional<User> findByToken(String token) {
        return Optional.ofNullable(loggedUsers.get(token));
    }

    public void delete(Integer id) {
        userRep.deleteById(id);
    }

    public User get(Integer id) {
        return userRep.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Page<User> list(PageRequest pageReq) {
        return userRep.findAll(pageReq);
    }

    public User update(User user) {
        User dbUser = userRep.findById(user.getId()).get();
        user.setPassword(dbUser.getPassword());
        return userRep.save(user);
    }

    public User create(User user) {
        user.setPassword(EncryptUtils.hashPassword(user.getPassword()).get());
        return userRep.save(user);
    }

    public Optional<User> login(String username, String password) throws NoSuchElementException {
        String token = UUID.randomUUID().toString();
        User user = new User();
        Optional<User> optDev = userRep.getUserByUsernameAndPassword(username,
                EncryptUtils.hashPassword(password).get());
        if (optDev.isPresent()) {
            user = optDev.get();
            user.setToken(token);
            loggedUsers.put(token, user);
        } else token = null;
        return Optional.of(user);
    }

    public void logout(User user) {
        String keyToRemove = null;
        for (Map.Entry<String, User> entry : loggedUsers.entrySet())
			if (entry.getValue() == user) {
				keyToRemove = entry.getKey();
				break;
			}
        if (keyToRemove != null) loggedUsers.remove(keyToRemove);
    }

    @Bean
    @Profile("local")
    public void initTestUser() {
        System.out.println("Creating test user...");
        User admin = User.builder()
        .name("Alexandre")
        .username("alexandre")
        .password("924d5413f06c0fba1ded3a11f61171ee").build();
        loggedUsers.put("7cd2f9e1-a6e9-4675-9176-b9219b0fd8d8", admin);
    }

}
