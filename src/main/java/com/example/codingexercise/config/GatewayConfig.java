package com.example.codingexercise.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GatewayConfig {

    private final Environment env;

    public GatewayConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.basicAuthentication(env.getProperty("herokuapp.products.username"), env.getProperty("herokuapp.products.password")).build();    
    }
}
