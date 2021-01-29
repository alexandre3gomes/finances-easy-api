package com.finances.bo

import com.finances.dto.UserDTO
import com.finances.entity.User
import com.finances.mapper.toDTO
import com.finances.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserBo(private val userRep: UserRepository) {

    fun managerUser(principal: Principal): UserDTO {
        val user: User? = userRep.getUserByUsername(principal.name)
        return user?.toDTO() ?: userRep.save(User("Need to be changed", principal.name)).toDTO()
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
        return userRep.save(User(user.id, user.name, user.username)).toDTO()
    }

    fun create(user: User): UserDTO {
        return userRep.save(User(user.id, user.name, user.username)).toDTO()
    }
}
