package com.wipro.cozyhaven.security;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

	@Configuration
	@OpenAPIDefinition(
	    info = @Info(
	        title = "Cozy Haven Stay API",
	        version = "1.0",
	        description = "Hotel Booking Management System"
	    ),
	    security = @SecurityRequirement(name = "basicAuth")
	)
	@SecurityScheme(
	    name = "basicAuth",
	    scheme = "basic",
	    type = SecuritySchemeType.HTTP,
	    in = SecuritySchemeIn.HEADER
	)
	public class SwaggerConfig {
	    // No code needed — annotations do everything
	}

