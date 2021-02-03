package com.finances.integration.bo

import com.finances.bo.UserBo
import com.finances.entity.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup

@SqlGroup(
    Sql("/db/users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
    Sql("/db/users_clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
)
@SpringBootTest
class UserBoTests {

    @Autowired
    lateinit var userBo: UserBo

    @Test
    fun testCreateUser() {
        val user = userBo.create(User("Test", "test@test.com"))
        assertThat(user).isNotNull
        assertThat(user.id).isEqualTo(3)
    }

    @Test
    fun testGetUser() {
        val user = userBo.get(1)
        assertThat(user).isNotNull
        assertThat(user.name).isEqualTo("admin")
        assertThat(user.username).isEqualTo("admin@test.com")
    }

    @Test
    fun testUpdateUser() {
        val user = User("user edited", "useredited@test.com")
        user.id = 2
        val editedUser = userBo.update(user)
        assertThat(editedUser).isNotNull
        assertThat(editedUser.name).isEqualTo("user edited")
        assertThat(editedUser.username).isEqualTo("useredited@test.com")
    }

    @Test
    fun testDeleteUser() {
        userBo.delete(2)
        assertThrows<NoSuchElementException> {
            userBo.get(2)
        }
    }

    @Test
    fun testListUsers() {
        val users = userBo.list(PageRequest.of(10, 10))
        assertThat(users).isNotNull
        assertThat(users.totalElements).isEqualTo(2)
    }
}
