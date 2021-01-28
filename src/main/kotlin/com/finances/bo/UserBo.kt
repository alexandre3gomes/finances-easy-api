package com.finances.bo

import com.finances.dto.UserDTO
import com.finances.entity.User
import com.finances.mapper.toDTO
import com.finances.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.stereotype.Service
import java.security.Principal

@Service
class UserBo(private val userRep: UserRepository) {

    fun managerUser(principal: Principal): UserDTO {
        val userInfo = (principal as OAuth2AuthenticationToken).principal as DefaultOidcUser
        val user: User? = userRep.getUserByUsername(userInfo.preferredUsername)
        return user?.toDTO() ?: userRep.save(User(userInfo.fullName, userInfo.preferredUsername)).toDTO()
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
