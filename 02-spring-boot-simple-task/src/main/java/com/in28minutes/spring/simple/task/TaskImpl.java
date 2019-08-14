package com.in28minutes.spring.simple.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TaskImpl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskImpl.class);

	public void performTask() {
		LOGGER.info("DO THE ONE TIME TASK HERE!");
	}
}
