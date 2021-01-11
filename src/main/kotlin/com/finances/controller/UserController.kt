package com.finances.controller

import com.finances.bo.UserBo
import com.finances.dto.UserDTO
import com.finances.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userBo: UserBo) {

    @PostMapping("")
    fun create(@RequestBody user: User): UserDTO = userBo.create(user)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Int) = userBo.delete(id)

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Int): UserDTO = userBo.get(id)

    @GetMapping("/current")
    fun getUser(@AuthenticationPrincipal user: User) = user

    @GetMapping("")
    fun list(@RequestParam(defaultValue = "0") page: Int,
             @RequestParam(defaultValue = "10") size: Int, @RequestParam(defaultValue = "name") order: String?,
             @RequestParam(defaultValue = "DESC") direction: Sort.Direction): Page<UserDTO> {
        return userBo.list(PageRequest.of(page, size, Sort.by(direction, order)))
    }

    @GetMapping("/logout")
    fun logout(@AuthenticationPrincipal user: User): Boolean {
        userBo.logout(user)
        return true
    }

    @PostMapping("/update")
    fun update(@RequestBody user: User): UserDTO = userBo.update(user)
}