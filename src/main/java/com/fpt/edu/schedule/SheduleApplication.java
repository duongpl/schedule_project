package com.fpt.edu.schedule;

import com.fpt.edu.schedule.ai.model.GeneticAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@EntityScan(basePackages = {"com.fpt.edu.schedule"})
@SpringBootApplication(scanBasePackages = {"com.fpt.edu"})
@AllArgsConstructor
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Configuration
public class SheduleApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext applicationContext;
	public static void main(String[] args) {
		SpringApplication.run(SheduleApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(applicationContext.getDisplayName());
		System.out.println(applicationContext.getId());

		GeneticAlgorithm myBean = applicationContext.getBean(GeneticAlgorithm.class);
		myBean.setGeneration(10);
		System.out.println(myBean.getGeneration());
		GeneticAlgorithm myBean1 = applicationContext.getBean(GeneticAlgorithm.class);
		myBean1.setGeneration(20);
		System.out.println(myBean1.getGeneration());
	}
}
