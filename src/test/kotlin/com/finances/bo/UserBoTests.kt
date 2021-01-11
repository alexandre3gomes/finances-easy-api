package com.finances.bo

import com.finances.entity.User
import com.finances.mapper.toEntity
import com.finances.repository.UserRepository
import com.finances.util.BuildMockDataUtil
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.test.util.AssertionErrors
import java.util.*

@ExtendWith(MockitoExtension::class)
class UserBoTests {

    @Mock
    private val userRepo: UserRepository? = null

    @InjectMocks
    private val userBo: UserBo? = null
    fun testLogin() {
        val userOpt: Optional<User> = Optional.of(BuildMockDataUtil.buildUser())
        Mockito.lenient().`when`(userRepo!!.getUserByUsernameAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(userOpt)
        val user = userBo!!.login("test", "test")
        AssertionErrors.assertTrue("User token not found in memory", userBo.findByToken(user.token).isPresent)
    }

    fun testLogout() {
        val userOpt: Optional<User> = Optional.of(BuildMockDataUtil.buildUser())
        Mockito.lenient().`when`(userRepo!!.getUserByUsernameAndPassword(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(userOpt)
        val user = userBo!!.login("test", "test")
        userBo.logout(user.toEntity())
        AssertionErrors.assertFalse("User is still present", userBo.findByToken(user.token).isPresent)
    }
}