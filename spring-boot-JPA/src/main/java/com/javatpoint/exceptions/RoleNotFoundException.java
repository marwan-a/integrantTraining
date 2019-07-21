package com.javatpoint.exceptions;

@SuppressWarnings("serial")
public class RoleNotFoundException extends Exception {
	public RoleNotFoundException(Long id) {
		super("Could not find Role with id: "+id);
	}
}
