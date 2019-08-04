package com.javatpoint.controllers;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.javatpoint.exceptions.UserNotFoundException;
import com.javatpoint.models.UserRecord;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@SuppressWarnings("unused")
@Component
public class UserResourceAssembler implements ResourceAssembler<UserRecord, Resource<UserRecord>> {

	  @Override
	  public Resource<UserRecord> toResource(UserRecord user) {

//	    try {
//			return new Resource<>(user,
//			  linkTo(methodOn(UserController.class).getUser(user.getUser_id())).withSelfRel(),
//			  linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
//		} catch (UserNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	  }
	}