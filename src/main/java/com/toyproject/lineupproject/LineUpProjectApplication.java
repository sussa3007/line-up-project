package com.toyproject.lineupproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

@ConfigurationPropertiesScan
@SpringBootApplication
@PropertySource("classpath:/env.yml")
public class LineUpProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(LineUpProjectApplication.class, args);
	}

}
