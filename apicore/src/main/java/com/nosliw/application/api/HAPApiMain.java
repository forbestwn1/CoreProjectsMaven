package com.nosliw.application.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
public class HAPApiMain {

	public static void main(String[] args) {
		
		new SpringApplicationBuilder(HAPApiMain.class)
	    .headless(false)
	    .run(args);

		
//        SpringApplication.run(HAPApiMain.class, args);
    }
	
	
}
