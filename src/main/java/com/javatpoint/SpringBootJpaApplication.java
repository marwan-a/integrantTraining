package com.javatpoint;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

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
		 Runnable myRunnable=new Runnable() {
				
				@Override
				public void run() {
					TwitterKafkaConsumer tfc=context.getBean(TwitterKafkaConsumer.class);
				       tfc.initialize();
				       tfc.consume();
				}
			};
			 Thread myThread=new Thread(myRunnable);
			 Thread myThread2;
			 boolean consumer=false;
			System.out.println("Do you want to close the app? type 'close' to exit, else continue");
		Scanner sc=new Scanner(System.in);
	   while(!sc.nextLine().equalsIgnoreCase("close")) 
	   {
		   System.out.println("Start tweet fetching? Press Y for yes, else no");
			if(sc.nextLine().equalsIgnoreCase("y"))
			{	
				System.out.println("Enter the number of tweets to fetch");
				int numTweets=Integer.parseInt(sc.nextLine());
				ArrayList<String> tags=new ArrayList<>();
				System.out.println("Enter tag #1 to fetch tweets with");
				tags.add(sc.nextLine());
				int i=2;
				while (true) {
					System.out.println("Enter more tags? press Y for Yes otherwise no");
					if(!sc.nextLine().equalsIgnoreCase("y"))
						break;
					System.out.println("Enter tag #" +i+" to fetch tweets with");
					tags.add(sc.nextLine());
					i++;				
				}
				 if (!consumer) {
					 myThread=new Thread(myRunnable);
					 myThread.start();
					 consumer=true;
				}
				 myThread2=new Thread(createRunnable(context, api, apiSecret, accessToken, accessTokenSecret, tags,numTweets));
				 myThread2.start();
				 myThread2.join();
				 System.out.println("fetched "+numTweets+" tweets.. fetch more tweets with the same tags? press y for yes, else no");	 
				 while (sc.nextLine().equalsIgnoreCase("y")) {
					 System.out.println("Enter the number of tweets to fetch");
					 numTweets=Integer.parseInt(sc.nextLine());
					 myThread2=new Thread(createRunnable(context, api, apiSecret, accessToken, accessTokenSecret, tags,numTweets));
					 myThread2.start();
					 myThread2.join();
					 System.out.println("fetched "+numTweets+" tweets.. fetch more tweets with the same tags? press y for yes, else no");
				}
			}
			System.out.println("Do you want to close the app? type 'close' to exit, else continue");
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
	private static Runnable createRunnable(ConfigurableApplicationContext context,String api, String apiSecret, String accessToken, String accessTokenSecret,ArrayList<String> tags, int numTweets){

	    Runnable aRunnable = new Runnable(){
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
					tfp.PushTwittermessage(producer,api, apiSecret, accessToken, accessTokenSecret,tags,numTweets);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	    };

	    return aRunnable;

	}
	@Bean
	public TwitterKafkaConsumer twitterConsumer() {
		return new TwitterKafkaConsumer();
	}
}