package com.javatpoint.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.TwitterKafkaConsumer;
import com.javatpoint.TwitterKafkaProducer;
import com.javatpoint.dto.TweetDto;
import com.javatpoint.dto.TweetFetchInfo;
import com.javatpoint.exceptions.TweetNotFoundException;
import com.javatpoint.mappers.TweetMapper;
import com.javatpoint.services.TweetService;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

@RestController
public class TweetController {
	@Autowired
	private TweetService tweetService;
	private TweetMapper mapper
	=Mappers.getMapper(TweetMapper.class);
	@Autowired
	private ConfigurableApplicationContext context;
	private static boolean consumer=false;	
	@PostMapping("/tweets/startTweetFetching")
	@CrossOrigin(origins = "http://localhost:4200")
	void startTweet(@RequestBody TweetFetchInfo tfi) throws InterruptedException
	{		System.out.println("in here");
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
		 if (!consumer) {
			 myThread=new Thread(myRunnable);
			 myThread.start();
			 consumer=true;
		}
		 ArrayList<String> tags=new ArrayList<>(Arrays.asList(tfi.getTag()));
		 myThread2=new Thread(createRunnable(context, tfi.getApi(), tfi.getApiSecret(), tfi.getAccessToken(), tfi.getAccessTokenSecret(), tags,tfi.getNumTweets()));
		 myThread2.start();
//		 myThread2.join();
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
	  @GetMapping("/tweets")
	  @CrossOrigin(origins = "http://localhost:4200")
	  public ArrayList<TweetDto> getAllTweets() {
	    return mapper.tweetsToDtos(tweetService.getAllTweets());
	  }
	  @GetMapping("/tweets/{id}")
	  @CrossOrigin(origins = "http://localhost:4200")
	  public TweetDto getTweet(@PathVariable String id) throws TweetNotFoundException {
	    return mapper.tweetToDto(tweetService.getTweetById(id)
	    		.orElseThrow(() -> new TweetNotFoundException(id)));
	  }
	  @GetMapping("/tweets/sentiment/{sentiment_score}")
	  @CrossOrigin(origins = "http://localhost:4200")
	  public ArrayList<TweetDto> getAllTweetsBySentiment(@PathVariable double sentiment_score) {
	    return mapper.tweetsToDtos(tweetService.getAllTweetsWithSentimentScore(sentiment_score));
	  }
	  @GetMapping("/tweets/sentiment")
	  @CrossOrigin(origins = "http://localhost:4200")
	  public double getAllSentimentAverage() {
		  double average = tweetService.getAllTweets().stream()
                  .mapToDouble(p -> p.getSentiment())
                  .average()
                  .orElse(0);
		  return average;
	  }
	  @GetMapping("/tweets/count")
	  @CrossOrigin(origins = "http://localhost:4200")
	  public int getAllTweetsCount()
	  {		
		  return tweetService.getAllTweets().size();
	  }
}
