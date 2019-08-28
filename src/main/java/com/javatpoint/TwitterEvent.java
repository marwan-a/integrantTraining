package com.javatpoint;

import lombok.Data;

@Data
public class TwitterEvent {
	private String tweet_id;
	private String tweet_text;
	private double sentiment_score;
	
	public TwitterEvent(String tweet_id,String tweet_text,double sentiment_score)
	{
		this.tweet_id=tweet_id;
		this.tweet_text=tweet_text;
		this.sentiment_score=sentiment_score;
	}
}
