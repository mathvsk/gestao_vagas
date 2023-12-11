package br.com.matheusviscki.gestao_vagas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
            .title("Gestão de Vagas API")
            .description("API responsável pela gestão de vagas")
            .version("v1")
        ).schemaRequirement("jwt_auth", creaSecurityScheme());
    }

    private SecurityScheme creaSecurityScheme() {
        return new SecurityScheme()
            .name("jwt_auth")
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT");
    }
}
