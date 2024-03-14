package com.logistics.snowapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SnowApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowApiApplication.class, args);
    }

}