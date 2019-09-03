package com.javatpoint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.ejml.simple.SimpleMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

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

@Component
public class TwitterKafkaProducer {
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	private static final String topic = "twitter-topic";
	static Properties props;
	static StanfordCoreNLP pipeline;
	public void initializeSentiment() {
		 // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and sentiment
		props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		pipeline = new StanfordCoreNLP(props);
	}
	public void PushTwittermessage(Producer<String, String> producer, String consumerKey, String consumerSecret, String token, String secret) throws InterruptedException {
		
        KeyedMessage<String, String> message=null;
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        //add some track terms
        endpoint.trackTerms(Lists.newArrayList("trump"));
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
                      int sent=0;
                      while(sent<5) {
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
                              final String TWITTER="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
                              SimpleDateFormat sf = new SimpleDateFormat(TWITTER,Locale.ENGLISH);
                              sf.setLenient(true);
                              Date created_at= sf.parse(JsonPath.parse(msg).read("created_at"));
                              TwitterEvent te=new TwitterEvent(this,JsonPath.parse(msg).read("id_str"),text,sentiment_score,created_at);
                              applicationEventPublisher.publishEvent(te);
                              sent++;
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
	
	
//       public static void main(String[] args) {
//    	   TwitterKafkaProducer tfp=new TwitterKafkaProducer();
//    	   System.out.println(args[0]);
//    	   System.out.println(args[1]);
//    	   System.out.println(args[2]);
//    	   System.out.println(args[3]);
//       Properties props = new Properties();
//       props.put("metadata.broker.list","localhost:9092");
//       props.put("serializer.class","kafka.serializer.StringEncoder");
//		props.put("bootstrap.servers", "localhost:9092");
//		tfp.initializeSentiment();
//       ProducerConfig producerConfig = new ProducerConfig(props);
//       Producer<String, String>producer = new Producer<String, String>(producerConfig);
//       try {	
//    	   tfp.PushTwittermessage(producer,args[0], args[1], args[2], args[3]);
//       } catch(InterruptedException e) {
//                      System.out.println(e);
//       }
//       }
}