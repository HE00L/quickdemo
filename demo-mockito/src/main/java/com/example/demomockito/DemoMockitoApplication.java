package com.example.demomockito;

import com.example.demomockito.config.UrlProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(UrlProperties.class)
public class DemoMockitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMockitoApplication.class, args);
    }

}
