package com.example.descriptioncud;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class DescriptionCudApplication {

    public static void main(String[] args) {
        SpringApplication.run(DescriptionCudApplication.class, args);
    }

}
