package com.javatpoint.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatpoint.models.Tweet;
import com.javatpoint.repositories.TweetRepository;

@Service
public class TweetService {
	@Autowired
	private TweetRepository tweetRepository;
	
	public List<Tweet> getAllTweets()
	{
		List<Tweet> allTweets=new ArrayList<>();
		tweetRepository.findAll().forEach(allTweets::add);
		return allTweets;
	}
	public Collection<Tweet> getAllTweetsWithTag(String tag)
	{
		return tweetRepository.getAllTweetsWithTag(tag);
	}
	public Optional<Tweet> getTweetById(String tweet_id)
	{
		return tweetRepository.findById(tweet_id);
	}
	public List<Tweet> getAllTweetsWithSentimentScore(double sentiment_score)
	{
		List<Tweet> allTweets=new ArrayList<>();
		tweetRepository.findAllBySentiment(sentiment_score).forEach(allTweets::add);
		return allTweets;
	}
	public int getTweetsCountWithSentimentScore(double sentiment_score) {
		return tweetRepository.countBySentiment(sentiment_score);
	}	
	public int getTweetsCountWithSentimentScoreAndTag(String tag,double sentiment_score) {
		return tweetRepository.countBySentimentAndTag(tag,sentiment_score);
	}	
}
