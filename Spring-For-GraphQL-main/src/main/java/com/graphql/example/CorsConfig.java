package com.graphql.example;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig {
   	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://127.0.0.1:5500"); // Ajusta la URL de tu aplicación cliente
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        source.registerCorsConfiguration("/graphql/**", config); // Ajusta la ruta del controlador GraphQL

        return new CorsFilter(source);
    }
	

}
