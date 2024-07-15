package br.jus.stj.skeletoApp.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.IntegerSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI openApiSpec() {
        return new OpenAPI()
                .info(new Info().title("Projeto Skeleto Backend Java SpringBoot")
                        .version("V.1.0")
                        .license(new License().name("Base de Dados em memória H2").url("http://localhost:8080/h2-console")))
                .externalDocs(new ExternalDocumentation()
                        .description("API Skeleto")
                        .url("https://github.com/acmourao/skeletoApp.git"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                                .addSchemas("ApiErrorResponse", new ObjectSchema()
                                        .addProperty("status", new IntegerSchema())
                                        .addProperty("code", new StringSchema())
                                        .addProperty("message", new StringSchema())
                                        .addProperty("fieldErrors", new ArraySchema().items(
                                                new Schema<ArraySchema>().$ref("ApiFieldError")))
                                )
                                .addSchemas("ApiFieldError", new ObjectSchema()
                                        .addProperty("code", new StringSchema())
                                        .addProperty("message", new StringSchema())
                                        .addProperty("property", new StringSchema())
                                        .addProperty("rejectedValue", new ObjectSchema())
                                        .addProperty("path", new StringSchema())
                                )
                );
    }

}
