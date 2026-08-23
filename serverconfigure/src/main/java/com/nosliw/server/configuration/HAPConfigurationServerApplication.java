package com.nosliw.server.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class HAPConfigurationServerApplication {

	public static void main(String[] args) {
	    SpringApplication.run(HAPConfigurationServerApplication.class, args);
	}
	
}
