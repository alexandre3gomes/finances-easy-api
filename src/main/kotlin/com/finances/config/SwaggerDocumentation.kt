package com.finances.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerDocumentation {

    @Bean
    fun api(): OpenAPI {
        return OpenAPI()
            .info(
                Info().title("Finances Easy API")
                    .description("API to be used by Finances Easy application")
                    .version("1.0")
                    .license(License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"))
            )
    }
}
