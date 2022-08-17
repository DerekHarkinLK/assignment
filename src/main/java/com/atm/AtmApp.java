package com.atm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = { "com.atm.controller" })
@ComponentScan(basePackages = { "com.atm.service" })
@ComponentScan(basePackages = { "com.atm.load" })
public class AtmApp {

	public static void main(String[] args) {
		SpringApplication.run(AtmApp.class, args);
	}

}