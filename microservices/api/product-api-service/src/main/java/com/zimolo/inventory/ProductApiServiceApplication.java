package com.zimolo.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.security.oauth2.resource.EnableOAuth2Resource;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableOAuth2Resource
@EnableAutoConfiguration
public class ProductApiServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApiServiceApplication.class, args);
    }
}
