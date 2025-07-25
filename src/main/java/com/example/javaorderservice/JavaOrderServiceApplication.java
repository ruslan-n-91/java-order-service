package com.example.javaorderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JavaOrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaOrderServiceApplication.class, args);
    }

}
