package com.javatpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;


@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class SpringBootJpaApplication {
	public static void main(String[] args) throws InterruptedException {
		 SpringApplication.run(SpringBootJpaApplication.class, args);        
	}
	@Bean
	public TwitterKafkaConsumer twitterConsumer() {
		return new TwitterKafkaConsumer();
	}
}