package com.tobeto.banking.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tobeto.banking"})
@EntityScan(basePackages = {"com.tobeto.banking.entities"})
@EnableJpaRepositories(basePackages = {"com.tobeto.banking.repositories"})
public class BankingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }
} 