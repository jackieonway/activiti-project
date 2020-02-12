package com.github.jackieonway.activiti;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {
        org.activiti.spring.boot.SecurityAutoConfiguration.class, SecurityAutoConfiguration.class
})
@MapperScan("com.github.jackieonway.activiti.dao")
public class ActivitiProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiProjectApplication.class, args);
    }
}
