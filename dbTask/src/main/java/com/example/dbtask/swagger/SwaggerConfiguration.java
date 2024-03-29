package com.example.dbtask.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("A1-DBTASK")
                        .description("""
            DB task with reading local fs
          """)
                        .version("v1.0")
                        .contact(new Contact()
                                .url("https://github.com/Smaktulka")));
    }
}