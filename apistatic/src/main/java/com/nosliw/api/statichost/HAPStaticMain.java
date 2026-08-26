package com.nosliw.api.statichost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
@ConfigurationPropertiesScan
public class HAPStaticMain {

	public static void main(String[] args) {
        SpringApplication.run(HAPStaticMain.class, args);
    }
	

}
