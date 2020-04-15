package com.fpt.edu.schedule;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@EntityScan(basePackages = {"com.fpt.edu.schedule"})
@SpringBootApplication(scanBasePackages = {"com.fpt.edu"})
@AllArgsConstructor
@EnableAsync
@Configuration
public class SheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SheduleApplication.class, args);
	}

}
