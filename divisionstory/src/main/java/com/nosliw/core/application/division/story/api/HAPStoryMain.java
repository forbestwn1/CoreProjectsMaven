package com.nosliw.core.application.division.story.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nosliw")
public class HAPStoryMain {

	public static void main(String[] args) {
        SpringApplication.run(HAPStoryMain.class, args);
    }
}
