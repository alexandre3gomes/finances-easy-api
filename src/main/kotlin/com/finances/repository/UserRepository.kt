package com.finances.repository

import com.finances.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.lang.Nullable

interface UserRepository : JpaRepository<User, Int> {

    @Nullable
    fun getUserByUsername(username: String): User
}
