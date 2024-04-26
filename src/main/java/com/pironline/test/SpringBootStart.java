package com.pironline.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.pironline.test")
public class SpringBootStart extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootStart.class, args);
    }
}
