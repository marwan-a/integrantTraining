package com.javatpoint.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.javatpoint.models.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, String>{
		Collection<Tweet> findAllBySentiment(double sentiment);
		int countBySentiment(double sentiment);
		
		String Q_GET_ALL_Tweets_With_Tag= 
"select tweet from TweetTag tag inner join tag.tweets tweet where tag.tweetTagName = :tag";
		@Query(Q_GET_ALL_Tweets_With_Tag)
		Collection<Tweet> getAllTweetsWithTag(@Param("tag")  String tag);
		String Q_GET_ALL_Tweets_With_Tag_And_Sentiment= 
"select count(*) from TweetTag tag inner join tag.tweets tweet where tag.tweetTagName = :tag and tweet.sentiment=:sentiment";
		@Query(Q_GET_ALL_Tweets_With_Tag_And_Sentiment)
		int countBySentimentAndTag(@Param("tag")  String tag,@Param("sentiment")double sentiment);
}
