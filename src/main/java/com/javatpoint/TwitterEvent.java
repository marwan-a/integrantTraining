package com.javatpoint;

import org.springframework.context.ApplicationEvent;

import lombok.Data;

@Data
public class TwitterEvent extends ApplicationEvent {
	private String tweet_id;
	private String tweet_text;
	private double sentiment_score;
	
	public TwitterEvent(Object source,String tweet_id,String tweet_text,double sentiment_score)
	{	super(source);
		this.tweet_id=tweet_id;
		this.tweet_text=tweet_text;
		this.sentiment_score=sentiment_score;
	}
}
