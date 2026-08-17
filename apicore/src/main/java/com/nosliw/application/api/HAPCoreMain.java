package com.nosliw.application.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
@ConfigurationPropertiesScan
public class HAPCoreMain {

	public static void main(String[] args) {
		
		new SpringApplicationBuilder(HAPCoreMain.class)
	    .headless(false)
	    .run(args);

		
//        SpringApplication.run(HAPApiMain.class, args);
    }
	
	
}
