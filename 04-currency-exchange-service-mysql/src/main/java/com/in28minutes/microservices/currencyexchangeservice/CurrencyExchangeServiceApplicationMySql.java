package com.in28minutes.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.in28minutes.microservices.currencyexchangeservice.resource.ExchangeValue;
import com.in28minutes.microservices.currencyexchangeservice.resource.ExchangeValueRepository;

@SpringBootApplication
public class CurrencyExchangeServiceApplicationMySql implements CommandLineRunner {

	@Autowired
	ExchangeValueRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeServiceApplicationMySql.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		long count = repository.count();

		if (count == 0) {
			repository.save(new ExchangeValue(10001L, "USD", "INR", BigDecimal.valueOf(60)));
			repository.save(new ExchangeValue(10002L, "EUR", "INR", BigDecimal.valueOf(70)));
			repository.save(new ExchangeValue(10003L, "AUD", "INR", BigDecimal.valueOf(20)));
		}
	}

}
