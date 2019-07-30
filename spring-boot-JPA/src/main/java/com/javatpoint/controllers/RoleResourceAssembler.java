package com.javatpoint.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.javatpoint.exceptions.RoleNotFoundException;
import com.javatpoint.models.Role;
@SuppressWarnings("unused")
@Component
public class RoleResourceAssembler implements ResourceAssembler<Role, Resource<Role>>{

	public Resource<Role> toResource(Role role) {

//	    try {
//			return new Resource<>(role,
//			  linkTo(methodOn(RoleController.class).getRole(role.getRole_id())).withSelfRel(),
//			  linkTo(methodOn(RoleController.class).getAllRoles()).withRel("roles"));
//		} catch ( RoleNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	  }
}
