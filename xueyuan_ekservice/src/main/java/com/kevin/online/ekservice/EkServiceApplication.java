package com.kevin.online.ekservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author kevin
 */
@EnableEurekaServer
@SpringBootApplication
public class EkServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EkServiceApplication.class,args);
    }
}
