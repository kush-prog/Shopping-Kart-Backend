package com.kush.shoppingkart.configurations;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customAPIConfig(){
        return new OpenAPI()
                .info(
                        new Info().title("Shopping Kart Backend")
                                .description("A scalable backend for an e-commerce platform with secure user authentication and product management.")
                )
//                .servers(Arrays.asList(new Server()
//                        .url("http://localhost:8080").description("local"))
//                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearer Auth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                        )
                );
    }
}
