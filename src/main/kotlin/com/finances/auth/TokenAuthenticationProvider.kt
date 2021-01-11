package com.finances.auth

import com.finances.bo.UserBo
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenAuthenticationProvider(private val auth: UserBo) : AbstractUserDetailsAuthenticationProvider() {

    override fun additionalAuthenticationChecks(d: UserDetails, auth: UsernamePasswordAuthenticationToken) {
        // Nothing to do
    }

    override fun retrieveUser(username: String, authentication: UsernamePasswordAuthenticationToken): UserDetails {
        val token = authentication.credentials
        return Optional.ofNullable(token).map { obj: Any -> obj.toString() }.flatMap { t: String? -> auth.findByToken(t) }.orElseThrow { UsernameNotFoundException("Cannot find user with authentication token=$token") }!!
    }
}