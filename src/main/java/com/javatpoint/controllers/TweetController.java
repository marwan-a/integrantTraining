package com.javatpoint.controllers;

import java.util.ArrayList;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.dto.TweetDto;
import com.javatpoint.exceptions.TweetNotFoundException;
import com.javatpoint.mappers.TweetMapper;
import com.javatpoint.services.TweetService;

@RestController
public class TweetController {
	@Autowired
	private TweetService tweetService;
	private TweetMapper mapper
	=Mappers.getMapper(TweetMapper.class);
	
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
}
