package com.house.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@SpringBootApplication
@ComponentScan("com.house.management")
public class HomeManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(HomeManagerApp.class, args);
    }

}

