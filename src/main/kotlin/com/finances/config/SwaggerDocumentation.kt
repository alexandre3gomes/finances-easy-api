package com.finances.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import java.util.*

@Configuration
class SwaggerDocumentation {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API).consumes(consumes).produces(produces)
    }

    companion object {
        val CONTACT = Contact("Alexandre Silva", "http://alexandre3gomes.github.io/",
                "alexandre3gomes@gmail.com")
        val DEFAULT_API = ApiInfo("finances-easy", "Finances Easy API", "1.0", "urn:tos", CONTACT,
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", ArrayList())
        val consumes: Set<String> = HashSet(listOf("application/json"))
        val produces: Set<String> = HashSet(listOf("application/json"))
    }
}