package com.kevin.online.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author kevin
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class UcServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcServiceApplication.class,args);
    }
}
