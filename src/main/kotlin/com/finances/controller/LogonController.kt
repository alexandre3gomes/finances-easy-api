package com.finances.controller

import com.finances.bo.UserBo
import com.finances.dto.UserDTO
import com.finances.entity.User
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/public/logon")
class LogonController(private val userBo: UserBo) {

    @PostMapping("/login")
    fun login(@RequestBody user: User): UserDTO = userBo.login(user.username, user.password)

    @GetMapping("/test")
    fun test(): String = "Works edit"

}