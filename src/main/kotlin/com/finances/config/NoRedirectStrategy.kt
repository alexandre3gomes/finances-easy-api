package com.finances.config

import org.springframework.security.web.RedirectStrategy
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class NoRedirectStrategy : RedirectStrategy {
    @Throws(IOException::class)
    override fun sendRedirect(arg0: HttpServletRequest, arg1: HttpServletResponse, arg2: String) {
        // TODO No redirect is required
    }
}