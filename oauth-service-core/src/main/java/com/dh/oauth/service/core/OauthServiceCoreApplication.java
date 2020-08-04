package com.dh.oauth.service.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.dh"})
public class OauthServiceCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthServiceCoreApplication.class, args);
	}

}
