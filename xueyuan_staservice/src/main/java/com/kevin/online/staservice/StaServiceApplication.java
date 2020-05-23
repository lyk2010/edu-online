package com.kevin.online.staservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author kevin
 */
@EnableEurekaClient
@SpringBootApplication
public class StaServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaServiceApplication.class,args);
    }
}
