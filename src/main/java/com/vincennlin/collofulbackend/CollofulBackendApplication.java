package com.vincennlin.collofulbackend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@OpenAPIDefinition(
		info = @Info(
				title = "Colloful API",
				version = "1.0",
				contact = @io.swagger.v3.oas.annotations.info.Contact(
						name = "vincennlin",
						email = "vincentagwa@gmail.com",
						url = "https://github.com/vincennlin"
				),
				license = @License(
						name = "Apache 2.0",
						url = "http://www.apache.org/licenses/LICENSE-2.0"
				)
		),
		externalDocs = @io.swagger.v3.oas.annotations.ExternalDocumentation(
				description = "Colloful Backend API Documentation",
				url = "https://github.com/vincennlin/colloful-backend"
		)
)
@EnableMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class CollofulBackendApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(CollofulBackendApplication.class, args);
	}

}
