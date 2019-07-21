package com.javatpoint.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.dto.RoleDto;
import com.javatpoint.exceptions.RoleNotFoundException;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.services.RoleService;
@RestController
public class RoleController {

	@Autowired  
    private RoleService roleService; 
	private RoleResourceAssembler assembler; 
	RoleController(RoleService roleService,
			RoleResourceAssembler assembler) {

   this.roleService = roleService;
   this.assembler = assembler;
 }

	//all roles with resource hateaos
//    @GetMapping("/roles")
//	  Resources<Resource<Role>> getAllRoles() {
//
//	    List<Resource<Role>> roles = roleService.getAllRoles().stream()
//	      .map(assembler::toResource)
//	      .collect(Collectors.toList());
//
//	    return new Resources<>(roles,
//	      linkTo(methodOn(RoleController.class).getAllRoles()).withSelfRel());
//	  }
//    @PostMapping("/roles/addrole")
//	  ResponseEntity<?> newRole(@RequestBody Role newRole) throws URISyntaxException {
//
//	    Resource<Role> resource = assembler.toResource(roleService.addRole(newRole));
//
//	    return ResponseEntity
//	      .created(new URI(resource.getId().expand().getHref()))
//	      .body(resource);
//	  }
//    @GetMapping("/roles/{id}")
//	  Resource<Role> getRole(@PathVariable Long id) throws RoleNotFoundException {
//
//    	Role role = roleService.getRole(id)
//	      .orElseThrow(() -> new RoleNotFoundException(id));
//
//	    return assembler.toResource(role);
//	  }
//	  @DeleteMapping("/roles/{id}")
//	  ResponseEntity<?> deleteRole(@PathVariable Long id) {
//
//		  roleService.deleteRole(id);
//
//	    return ResponseEntity.noContent().build();
//	  }
	
	@GetMapping("/roles")
	  ArrayList<RoleDto> getAllRoles() {
		List<Role> roles=roleService.getAllRoles();
		ArrayList<RoleDto> rolesDto=new ArrayList<>();
		for (int i = 0; i < roles.size(); i++) {
			RoleDto tempDto=new RoleDto();
			tempDto.setName(roles.get(i).getName());
			ArrayList<String> privileges=new ArrayList<>();
			ArrayList<Privilege> rolePrivileges=new ArrayList<>(roles.get(i).getPrivileges());
			for (int j = 0; j < rolePrivileges.size(); j++) {
				 Privilege p=rolePrivileges.get(j);
				 privileges.add(p.getName());
			}
			tempDto.setPrivileges(privileges);
			rolesDto.add(tempDto);
		}
	    return rolesDto;
	  }

	  @PostMapping("/admin/roles/addrole")
	  Role newRole(@RequestBody Role newRole) {
	    return roleService.addRole(newRole);
	  }

	  // Single item

	  @GetMapping("/roles/{id}")
	  RoleDto getRole(@PathVariable Long id) throws RoleNotFoundException {
		  Role role=roleService.getRole(id)
			      .orElseThrow(() -> new RoleNotFoundException(id));
		  RoleDto roleDto=new RoleDto();
		  roleDto.setName(role.getName());
		  ArrayList<String> privileges=new ArrayList<>();
			ArrayList<Privilege> rolePrivileges=new ArrayList<>(role.getPrivileges());
			for (int j = 0; j < rolePrivileges.size(); j++) {
				 Privilege p=rolePrivileges.get(j);
				 privileges.add(p.getName());
			}
			roleDto.setPrivileges(privileges);
		  return roleDto;
	  }
	  @DeleteMapping("/admin/roles/{id}")
	  void deleteRole(@PathVariable Long id) {
		  System.out.println("in delete");
	    roleService.deleteRole(id);
	  }
}
