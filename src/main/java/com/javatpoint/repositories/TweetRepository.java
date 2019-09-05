package com.javatpoint.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatpoint.models.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, String>{
		Collection<Tweet> findAllBySentiment(double sentiment);
}
