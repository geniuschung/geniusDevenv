package com.genius.devenv;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.genius.devenv.repository")
public class DevenvApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevenvApplication.class, args);
    }

}
