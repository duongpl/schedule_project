package com.fpt.edu.schedule;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.fpt.edu.schedule"})
@SpringBootApplication(scanBasePackages = {"com.fpt.edu"})
@AllArgsConstructor
public class SheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SheduleApplication.class, args);
	}

}
