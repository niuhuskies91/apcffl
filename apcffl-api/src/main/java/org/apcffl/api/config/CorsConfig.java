package org.apcffl.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer  {
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("GET","POST","HEAD","OPTIONS","PUT","DELETE")
        .allowedHeaders("Content-Type","X-Requested-With","X-Api-Key","accept","Origin","Access-Control-Request-Method","Access-Control-Request-Headers")
        .exposedHeaders("Access-Control-Allow-Origin")
        .allowCredentials(true);
    }
}