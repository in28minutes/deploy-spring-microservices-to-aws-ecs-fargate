package com.in28minutes.microservices.currencyconversionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder;

@SpringBootApplication
public class CurrencyConversionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyConversionServiceApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		// return new RestTemplate();
		return new RestTemplate(clientHttpRequestFactory());

	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
				HttpClientBuilder.create().useSystemProperties().build());
		factory.setReadTimeout(10000);
		factory.setConnectTimeout(2000);
		factory.setConnectionRequestTimeout(2000);
		return factory;
	}

}