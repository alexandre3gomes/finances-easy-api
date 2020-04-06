package net.finance.bo;

import net.finance.entity.User;
import net.finance.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UserBoTests {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserBo userBo;

    @Test
    public void testLogin() {
        Optional userOpt = Optional.of(createUser());
        Mockito.lenient().when(userRepo.getUserByUsernameAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(userOpt);
        Optional<User> user = userBo.login("test", "test");
        Assert.assertTrue("User token not found in memory", user.isPresent() && userBo.findByToken(user.get().getToken()).isPresent());
    }

    @Test
    public void testLogout() {
        Optional userOpt = Optional.of(createUser());
        Mockito.lenient().when(userRepo.getUserByUsernameAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(userOpt);
        Optional<User> user = userBo.login("test", "test");
        user.ifPresent((userLogged -> userBo.logout(userLogged)));
        Assert.assertFalse("User is still present", userBo.findByToken(user.get().getToken()).isPresent());
    }

    private static User createUser() {
        return User.builder()
                .name("Test User")
                .username("test").build();
    }
}
