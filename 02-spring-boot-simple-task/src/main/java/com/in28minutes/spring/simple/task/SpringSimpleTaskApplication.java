package com.in28minutes.spring.simple.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringSimpleTaskApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringSimpleTaskApplication.class, args);
		TaskImpl task = applicationContext.getBean(TaskImpl.class);
		task.performTask();
	}
}