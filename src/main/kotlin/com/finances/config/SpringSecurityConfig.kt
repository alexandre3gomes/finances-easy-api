package com.finances.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
class SpringSecurityConfig() : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        val publicPaths = listOf(PUBLIC_URLS, ACTUATOR, SWAGGER_UI, SWAGGER_API, SWAGGER_WEBJARS, SWAGGER_CONFIG, SWAGGER_RESOURCES)
        http.authorizeRequests()
            .requestMatchers(OrRequestMatcher(publicPaths))
            .permitAll()
            .requestMatchers(PROTECTED_URLS)
            .authenticated()
            .and()
            .oauth2Login()
            .and()
            .oauth2ResourceServer()
            .jwt()
    }

    companion object {
        private val PUBLIC_URLS: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/public/**"))
        private val SWAGGER_UI: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/swagger-ui/**"))
        private val SWAGGER_API: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/v3/api-docs/**"))
        private val SWAGGER_WEBJARS: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/webjars/**"))
        private val SWAGGER_CONFIG: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/configuration/**"))
        private val SWAGGER_RESOURCES: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/swagger-resources/**"))
        private val ACTUATOR: RequestMatcher = OrRequestMatcher(AntPathRequestMatcher("/actuator/**"))
        private val PROTECTED_URLS: RequestMatcher = NegatedRequestMatcher(PUBLIC_URLS)
    }
}
