package com.finances.bo

import com.finances.dto.UserDTO
import com.finances.entity.User
import com.finances.mapper.toDTO
import com.finances.repository.UserRepository
import com.finances.utils.EncryptUtils.hashPassword
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserBo(private val userRep: UserRepository) {

    private val loggedUsers: MutableMap<String?, User> = HashMap()

    fun findByToken(token: String?): Optional<User> {
        return Optional.ofNullable(loggedUsers[token])
    }

    fun delete(id: Int) {
        userRep.deleteById(id)
    }

    fun get(id: Int): UserDTO {
        return userRep.findById(id).orElseThrow { NoSuchElementException() }.toDTO()
    }

    fun list(pageReq: PageRequest): Page<UserDTO> {
        return userRep.findAll(pageReq).map(User::toDTO)
    }

    fun update(user: User): UserDTO {
        val dbUser: User = userRep.findById(user.id).get()
        return userRep.save(User(user.id, user.name, user.username, dbUser.password)).toDTO()
    }

    fun create(user: User): UserDTO {
        return userRep.save(User(user.id, user.name, user.username, hashPassword(user.password).get())).toDTO()
    }

    @Throws(NoSuchElementException::class)
    fun login(username: String, password: String): UserDTO {
        val token: String = UUID.randomUUID().toString()
        var user = User()
        val optDev: Optional<User> = userRep.getUserByUsernameAndPassword(username,
                hashPassword(password).get())
        if (optDev.isPresent) {
            user = optDev.get()
            user.token = token
            loggedUsers[token] = user
        }
        return user.toDTO()
    }

    fun logout(user: User) {
        var keyToRemove: String? = null
        for ((key, value) in loggedUsers) if (value === user) {
            keyToRemove = key
            break
        }
        if (keyToRemove != null) loggedUsers.remove(keyToRemove)
    }

    @Bean
    @Profile("local")
    fun initTestUser() {
        println("Creating test user...")
        val admin = User("Alexandre", "alexandre", "924d5413f06c0fba1ded3a11f61171ee")
        loggedUsers["7cd2f9e1-a6e9-4675-9176-b9219b0fd8d8"] = admin
    }
}