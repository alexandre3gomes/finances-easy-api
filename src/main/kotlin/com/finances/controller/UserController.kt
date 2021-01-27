package com.finances.controller

import com.finances.bo.UserBo
import com.finances.dto.UserDTO
import com.finances.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/user")
class UserController(private val userBo: UserBo) {

    @GetMapping("/current")
    fun getUser(principal: Principal): UserDTO {
        return userBo.managerUser(principal)
    }
}