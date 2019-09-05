package com.javatpoint.dto;

import lombok.Data;

@Data
public class TweetDto {
	private String tweet_id;
	private String tweet_text;
	private String sentiment;
	private String created_at;
}
