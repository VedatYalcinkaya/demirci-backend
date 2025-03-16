package com.demirciyazilim.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.demirciyazilim"})
@EntityScan(basePackages = {"com.demirciyazilim.entities"})
@EnableJpaRepositories(basePackages = {"com.demirciyazilim.repositories"})
public class DemirciBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemirciBackendApplication.class, args);
    }
}