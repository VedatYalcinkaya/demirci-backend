package com.tobeto.banking.webapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger konfigürasyonu
 */
@Configuration
public class SwaggerConfig {
    
    @Value("${application.title}")
    private String title;
    
    @Value("${application.version}")
    private String version;
    
    @Value("${application.description}")
    private String description;
    
    /**
     * OpenAPI konfigürasyonu
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                        .contact(new Contact()
                                .name("Tobeto Banking")
                                .email("info@tobeto.com")
                                .url("https://tobeto.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server"),
                        new Server().url("https://api.tobeto.com").description("Production Server")
                ));
    }
} 