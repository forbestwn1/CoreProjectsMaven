package com.nosliw.application.datasource.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
@ConfigurationPropertiesScan
public class HAPDataSourceMain {


	public static void main(String[] args) {
		
		new SpringApplicationBuilder(HAPDataSourceMain.class)
	    .headless(false)
	    .run(args);
    }

	
}
