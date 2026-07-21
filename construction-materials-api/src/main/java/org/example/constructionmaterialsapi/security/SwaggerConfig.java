package org.example.constructionmaterialsapi.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Construction Materials API")
                        .version("1.0")
                        .description("Tikinti materiallari idareetme sistemi ucun REST API Senedlesdirilmesi"));
    }
}