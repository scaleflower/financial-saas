package com.fs.transservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fs.transservice.mapper")
public class TransServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransServiceApplication.class, args);
    }
}
