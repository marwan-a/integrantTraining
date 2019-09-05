package com.javatpoint.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="tweets")
public class Tweet {
	
	@Id
	@Column(nullable = false, unique = true,name="tweet_id")
	private String tweet_id;
	@Column(nullable = false,name="tweet_text")
	private String tweet_text;
	@Column(nullable = false,name="sentiment_score")
	private double sentiment;
	@Column(nullable = false,name="created_at")
	private Date created_at;
}
