package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}

	// Spring Cloud Sleuth uses request headers to propagate trace-id & span-id.
	// Creating a bean for RestTemplate allows Spring Cloud Sleuth to inject the
	// headers into RestTemplate.

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
