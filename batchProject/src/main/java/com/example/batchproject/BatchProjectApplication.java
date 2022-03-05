package com.example.batchproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class BatchProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchProjectApplication.class, args);
    }

}
