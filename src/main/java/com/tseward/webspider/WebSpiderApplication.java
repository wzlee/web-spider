package com.tseward.webspider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.tseward.webspider.mapper*")
@EnableScheduling
public class WebSpiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSpiderApplication.class, args);
    }
}
