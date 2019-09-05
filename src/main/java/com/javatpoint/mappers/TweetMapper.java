package com.javatpoint.mappers;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.Mapper;

import com.javatpoint.dto.TweetDto;
import com.javatpoint.models.Tweet;

@Mapper(componentModel="spring")
public interface TweetMapper {
	TweetDto tweetToDto(Tweet tweet);
	Tweet dtoToTweet(TweetDto tweetDto);
	ArrayList<TweetDto> tweetsToDtos(Collection<Tweet> tweets);
	Collection<Tweet> dtosToTweets(ArrayList<TweetDto> tweetDtos);
}
