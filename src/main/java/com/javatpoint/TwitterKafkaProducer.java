package com.javatpoint;

//import java.util.Properties;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.LinkedBlockingQueue;
//
//import kafka.producer.KeyedMessage;
//import kafka.producer.ProducerConfig;
//
//import com.google.common.collect.Lists;
//import com.twitter.hbc.ClientBuilder;
//import com.twitter.hbc.core.Client;
//import com.twitter.hbc.core.Constants;
//import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
//import com.twitter.hbc.core.processor.StringDelimitedProcessor;
//import com.twitter.hbc.httpclient.auth.Authentication;
//import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import kafka.producer.KeyedMessage;

public class TwitterKafkaProducer {
	private static final String topic = "twitter-topic";
	public static void PushTwittermessage(Producer<String, String> producer, String consumerKey, String consumerSecret, String token, String secret) throws InterruptedException {
		
        KeyedMessage<String, String> message=null;
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        //add some track terms
        endpoint.trackTerms(Lists.newArrayList("twitterapi", "messi","ronaldo"));
        Authentication auth = new OAuth1(consumerKey,consumerSecret,token,secret);
        //create a new BasicClient. By default gzip is enabled.
        Client client = new ClientBuilder()
        			.hosts(Constants.STREAM_HOST)
                       .endpoint(endpoint)
                       .authentication(auth)
                       .processor(new StringDelimitedProcessor(queue))
                       .build();               
                      //establish a connection
                      client.connect();
                      //do whatever needs to be done with messages
                      for (int msgRead=0 ; msgRead<1000 ; msgRead++) {
                      try {
                      String msg = queue.take();
                      System.out.println(msg);
                      message = new KeyedMessage<String, String> (topic, queue.take());
                      } catch (InterruptedException e) {
                                     e.getStackTrace();
                      }
                      producer.send(message);
       }    
       producer.close();
       client.stop();
}
       public static void main(String[] args) {
       Properties props = new Properties();
       props.put("metadata.broker.list","localhost:9092");
       props.put("serializer.class","kafka.serializer.StringEncoder");
		props.put("bootstrap.servers", "localhost:9092");

       ProducerConfig producerConfig = new ProducerConfig(props);
       Producer<String, String>producer = new Producer<String, String>(producerConfig);
       try {	
                      TwitterKafkaProducer.PushTwittermessage(producer,args[0], args[1], args[2], args[3]);
       } catch(InterruptedException e) {
                      System.out.println(e);
       }
       }
}