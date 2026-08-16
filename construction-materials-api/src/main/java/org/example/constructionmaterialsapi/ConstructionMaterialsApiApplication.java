package org.example.constructionmaterialsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity
@EnableCaching
@EnableScheduling
@EnableAsync
public class ConstructionMaterialsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConstructionMaterialsApiApplication.class, args);
    }

}
