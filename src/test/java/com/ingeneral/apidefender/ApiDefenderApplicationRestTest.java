package com.ingeneral.apidefender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ApiDefenderApplicationRestTest {

    public static void main(String[] args) {
        SpringApplication.run(ApiDefenderApplicationRestTest.class, args);
    }
}
