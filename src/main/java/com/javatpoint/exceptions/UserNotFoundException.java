package com.javatpoint.exceptions;

@SuppressWarnings("serial")
public class UserNotFoundException extends Exception {

	public UserNotFoundException(Long id) {
		super("Could not find user with id: "+id);
	}

}
