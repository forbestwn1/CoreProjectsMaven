package com.nosliw.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
public class HAPApplication {

	public static void main(String[] args) {
        SpringApplication.run(HAPApplication.class, args);
    }
	
	
}
