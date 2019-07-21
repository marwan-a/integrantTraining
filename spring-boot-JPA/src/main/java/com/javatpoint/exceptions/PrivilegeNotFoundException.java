package com.javatpoint.exceptions;

@SuppressWarnings("serial")
public class PrivilegeNotFoundException extends Exception {
	public PrivilegeNotFoundException(Long id) {
		super("Could not find Privilege with id: "+id);
	}
}
