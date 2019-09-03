package com.javatpoint;

import java.util.Properties;
import java.util.Scanner;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;


@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class SpringBootJpaApplication {
	public static void main(String[] args) throws InterruptedException {
		 ConfigurableApplicationContext context =SpringApplication.run(SpringBootJpaApplication.class, args);
		 String api=args[1];
		 String apiSecret=args[2];
		 String accessToken=args[3];
		 String accessTokenSecret=args[4];
		 Runnable myRunnable1=new Runnable() {
				
				@Override
				public void run() {
					TwitterKafkaConsumer tfc=context.getBean(TwitterKafkaConsumer.class);
				       tfc.initialize();
				       tfc.consume();
				}
			};
		 Thread myThread=new Thread(myRunnable1);
		 Runnable myRunnable2=new Runnable() {
				
				@Override
				public void run() {
					TwitterKafkaProducer tfp= context.getBean(TwitterKafkaProducer.class);
				       Properties props = new Properties();
				       props.put("metadata.broker.list","localhost:9092");
				       props.put("serializer.class","kafka.serializer.StringEncoder");
						props.put("bootstrap.servers", "localhost:9092");
						tfp.initializeSentiment();
				       ProducerConfig producerConfig = new ProducerConfig(props);
				       Producer<String, String>producer = new Producer<String, String>(producerConfig);
				       try {
						tfp.PushTwittermessage(producer,api, apiSecret, accessToken, accessTokenSecret);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			};
		 Thread myThread2=new Thread(myRunnable2);
		 myThread.start();
		 myThread2.start();
		 myThread2.join();
		 System.out.println("fetched 500 tweets.. fetch more tweets? press y for yes, else no");
		 Scanner sc=new Scanner(System.in);
		 while (sc.nextLine().equalsIgnoreCase("y")) {
			 myThread2=new Thread(myRunnable2);
			 myThread2.start();
			 myThread2.join();
			 System.out.println("fetched 500 tweets.. fetch more tweets? press y for yes, else no");
		}
		 System.out.println("closing application");
		 sc.close();
		 Thread.currentThread().interrupt();
		 int exitCode = SpringApplication.exit(context, new ExitCodeGenerator() {
	            @Override
	            public int getExitCode() {
	                return 0;
	            }
	        });
	        System.exit(exitCode);
	}

	@Bean
	public TwitterKafkaConsumer twitterConsumer() {
		return new TwitterKafkaConsumer();
	}
}