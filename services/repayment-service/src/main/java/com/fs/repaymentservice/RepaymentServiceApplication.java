package com.fs.repaymentservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.fs.repaymentservice.mapper")
@ComponentScan(basePackages = {"com.fs.repaymentservice", "com.fs.common"})
public class RepaymentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RepaymentServiceApplication.class, args);
    }
}
