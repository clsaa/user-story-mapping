package com.clsaa.homework.usm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author joyren
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan("com.clsaa")
public class UsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsmApplication.class, args);
    }
}

