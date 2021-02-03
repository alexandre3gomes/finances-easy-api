package com.finances.config

import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

@Profile("local")
@Component
@Order(999)
class TokenFilter : Filter {

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val req = SecurityContextHolder.getContext().authentication
        try {
            println((req.principal as DefaultOidcUser).idToken.tokenValue)
        } catch (ex: Exception) {
            println(ex)
        }
        chain!!.doFilter(request, response)
    }
}
