package com.javatpoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatpoint.models.TweetTag;

public interface TweetTagRepository extends JpaRepository<TweetTag, Integer> {
	TweetTag findByTweetTagName(String tweet_tag_name);
}
