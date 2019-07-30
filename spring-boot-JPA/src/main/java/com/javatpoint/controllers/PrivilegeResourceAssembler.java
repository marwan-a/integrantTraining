package com.javatpoint.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.exceptions.PrivilegeNotFoundException;
import com.javatpoint.models.Privilege;
@SuppressWarnings("unused")
@Component
public class PrivilegeResourceAssembler implements ResourceAssembler<Privilege, Resource<Privilege>> {
	  public Resource<Privilege> toResource(Privilege privilege) {

//	    try {
//			return new Resource<>(privilege,
//			  linkTo(methodOn(PrivilegeController.class).getPrivilege(privilege.getPrivilege_id())).withSelfRel(),
//			  linkTo(methodOn(PrivilegeController.class).getAllPrivileges()).withRel("privileges"));
//		} catch ( PrivilegeNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return null;
	  }
	  public Resource<PrivilegeDto> toDtoResource(PrivilegeDto privilege) {

//		    try {
//				return new Resource<>(privilege,
//				  linkTo(methodOn(PrivilegeController.class).getPrivilege(privilege.getId())).withSelfRel(),
//				  linkTo(methodOn(PrivilegeController.class).getAllPrivileges()).withRel("privileges"));
//			} catch ( PrivilegeNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return null;
		  }
}
