package com.javatpoint.exceptions;

@SuppressWarnings("serial")
public class TweetNotFoundException extends Exception{
	public TweetNotFoundException(String id)
	{
		super("Could not find tweet with id: "+id);
	}
}
