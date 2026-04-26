package com.nosliw.application.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
public class HAPApiMain {

	public static void main(String[] args) {
        SpringApplication.run(HAPApiMain.class, args);
    }
	
	
}
