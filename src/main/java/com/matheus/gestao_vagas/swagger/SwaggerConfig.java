package com.matheus.gestao_vagas.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestão de Vagas API")
                        .version("1.0.0")
                        .description("API para gestão de vagas de emprego"))
                .schemaRequirement("jwt_auth", securityScheme());
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("jwt_auth")
                .scheme("bearer")
                .bearerFormat("JWT")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER);
    }
}
