package com.javatpoint.exceptions;

@SuppressWarnings("serial")
public class PasswordsNotMatchingException extends Exception {
	public PasswordsNotMatchingException(String message)
	{
		super(message);
	}
	public PasswordsNotMatchingException()
	{
		super();
	}
}
