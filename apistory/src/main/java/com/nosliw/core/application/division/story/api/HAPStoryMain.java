package com.nosliw.core.application.division.story.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
@ConfigurationPropertiesScan
public class HAPStoryMain {

	public static void main(String[] args) {
        SpringApplication.run(HAPStoryMain.class, args);
    }
}
