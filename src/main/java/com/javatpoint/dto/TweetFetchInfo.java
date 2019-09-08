package com.javatpoint.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class TweetFetchInfo {
	private String api;
	private String apiSecret;
	private String accessToken;
	private String accessTokenSecret;
	private int numTweets;
	private String tag;
}
