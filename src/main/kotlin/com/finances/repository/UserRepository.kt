package com.finances.repository

import com.finances.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Int> {

    fun getUserByUsernameAndPassword(username: String, password: String): Optional<User>
}