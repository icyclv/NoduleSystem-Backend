package com.neu.nodulesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.neu.nodulesystem.mapper")
@SpringBootApplication
public class NoduleSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoduleSystemApplication.class, args);
       
    }

}
