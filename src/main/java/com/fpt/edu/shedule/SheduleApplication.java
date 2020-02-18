package com.fpt.edu.shedule;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"com.fpt.edu.schedule.model"})
@SpringBootApplication(scanBasePackages = {"com.fpt"})
@AllArgsConstructor
public class SheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SheduleApplication.class, args);
	}

}
