package com.javatpoint;

import java.util.ArrayList;

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

import org.ejml.simple.SimpleMatrix;

import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class TwitterKafkaProducer {
	private static final String topic = "twitter-topic";
	static Properties props;
	static StanfordCoreNLP pipeline;
	private static ArrayList<MyEventListener> listeners = new ArrayList<MyEventListener>();
	public static void initializeSentiment() {
		 // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and sentiment
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(props);
	}
	public static void addListener(MyEventListener toAdd) {
        listeners.add(toAdd);
    }
	public static void PushTwittermessage(Producer<String, String> producer, String consumerKey, String consumerSecret, String token, String secret) throws InterruptedException {
		
        KeyedMessage<String, String> message=null;
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        //add some track terms
        endpoint.trackTerms(Lists.newArrayList("sad","angry", "trump","laugh","congratulations"));
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
                      //only english tweets
                      try {
                    	  if(JsonPath.parse(msg).read("lang").equals("en"))
                          {
                              String text;
                        	  //check for extended tweet
                              boolean truncated=JsonPath.parse(msg).read("truncated");
                              if(truncated)
                            	  text=JsonPath.parse(msg).read("extended_tweet.full_text");
                              else
                            	  text=JsonPath.parse(msg).read("text");     
                              double sentiment_score=getSentimentResult(text).getSentimentScore();
                              String toSend=text+System.getProperty("line.separator")
                              			+"Sentiment score: " + getSentimentResult(text).getSentimentScore()+System.getProperty("line.separator")
                              			+"Sentiment type: " + getSentimentResult(text).getSentimentType();
                              message = new KeyedMessage<String, String> (topic, toSend);
                              producer.send(message);
                              for (MyEventListener hl : listeners)
                                  hl.onMyEvent(new TwitterEvent(JsonPath.parse(msg).read("id_str"),text,sentiment_score));
                          }
					} catch (Exception e) {
						// TODO: handle exception
					}
                      } catch (InterruptedException e) {
                                     e.getStackTrace();
                      }
       }    
       producer.close();
       client.stop();
	}
	public static SentimentResult getSentimentResult(String text) {

		SentimentResult sentimentResult = new SentimentResult();
		SentimentClassification sentimentClass = new SentimentClassification();

		if (text != null && text.length() > 0) {
			
			// run all Annotators on the text
			Annotation annotation = pipeline.process(text);

			for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
				// this is the parse tree of the current sentence
				Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
				SimpleMatrix sm = RNNCoreAnnotations.getPredictions(tree);
				String sentimentType = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

				sentimentClass.setVeryPositive((double)Math.round(sm.get(4) * 100d));
				sentimentClass.setPositive((double)Math.round(sm.get(3) * 100d));
				sentimentClass.setNeutral((double)Math.round(sm.get(2) * 100d));
				sentimentClass.setNegative((double)Math.round(sm.get(1) * 100d));
				sentimentClass.setVeryNegative((double)Math.round(sm.get(0) * 100d));
				
				sentimentResult.setSentimentScore(RNNCoreAnnotations.getPredictedClass(tree));
				sentimentResult.setSentimentType(sentimentType);
				sentimentResult.setSentimentClass(sentimentClass);
			}

		}


		return sentimentResult;
	}
	
	
       public static void main(String[] args) {
       Properties props = new Properties();
       props.put("metadata.broker.list","localhost:9092");
       props.put("serializer.class","kafka.serializer.StringEncoder");
		props.put("bootstrap.servers", "localhost:9092");
		TwitterKafkaProducer.initializeSentiment();
       ProducerConfig producerConfig = new ProducerConfig(props);
       Producer<String, String>producer = new Producer<String, String>(producerConfig);
       MyEventHandler mev=new MyEventHandler();
       TwitterKafkaProducer.addListener(mev);
       try {	
                      TwitterKafkaProducer.PushTwittermessage(producer,args[0], args[1], args[2], args[3]);
       } catch(InterruptedException e) {
                      System.out.println(e);
       }
       }
}