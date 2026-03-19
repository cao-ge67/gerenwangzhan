package com.cycle.youth.traetest1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cycle.youth.traetest1.mapper")
public class Traetest1Application {

    public static void main(String[] args) {
        SpringApplication.run(Traetest1Application.class, args);
    }

}