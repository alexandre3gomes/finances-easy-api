package com.finances.bo;

import com.finances.util.BuildMockDataUtil;
import com.finances.entity.User;
import com.finances.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UserBoTests {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserBo userBo;

    public void testLogin() {
        Optional userOpt = Optional.of(BuildMockDataUtil.buildUser());
        Mockito.lenient().when(userRepo.getUserByUsernameAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(userOpt);
        Optional<User> user = userBo.login("test", "test");
        assertTrue("User token not found in memory", user.isPresent() && userBo.findByToken(user.get().getToken()).isPresent());
    }

    public void testLogout() {
        Optional userOpt = Optional.of(BuildMockDataUtil.buildUser());
        Mockito.lenient().when(userRepo.getUserByUsernameAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(userOpt);
        Optional<User> user = userBo.login("test", "test");
        user.ifPresent((userLogged -> userBo.logout(userLogged)));
        assertFalse("User is still present", userBo.findByToken(user.get().getToken()).isPresent());
    }
}
