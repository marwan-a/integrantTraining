package com.javatpoint.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.dto.RoleDto;
import com.javatpoint.exceptions.RoleNotFoundException;
import com.javatpoint.mappers.RoleMapper;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.services.PrivilegeService;
import com.javatpoint.services.RoleService;
@SuppressWarnings("unused")
@RestController
public class RoleController {

	@Autowired  
    private RoleService roleService; 
	@Autowired  
    private PrivilegeService privilegeService; 
	private RoleMapper mapper
    = Mappers.getMapper(RoleMapper.class);
	RoleController(RoleService roleService) {

   this.roleService = roleService;
 }

	  @GetMapping("/roles")
	  ArrayList<RoleDto> getAllRoles() {
	    return mapper.rolesToDtos(roleService.getAllRoles(), privilegeService);
	  }
	  @PostMapping("/admin/roles/addrole")
	  RoleDto newRole(@RequestBody Role newRole) {
	    return mapper.roleToDto(roleService.addRole(newRole), privilegeService);
	  }
	  @GetMapping("/roles/{id}")
	  RoleDto getRole(@PathVariable Long id) throws RoleNotFoundException {
		  return mapper.roleToDto(roleService.getRole(id)
			      .orElseThrow(() -> new RoleNotFoundException(id)), privilegeService);
	  }
	  @DeleteMapping("/admin/roles/{id}")
	  void deleteRole(@PathVariable Long id) {
	    roleService.deleteRole(id);
	  }

		//all roles with resource hateaos
//	    @GetMapping("/roles")
//		  Resources<Resource<Role>> getAllRoles() {
	//
//		    List<Resource<Role>> roles = roleService.getAllRoles().stream()
//		      .map(assembler::toResource)
//		      .collect(Collectors.toList());
	//
//		    return new Resources<>(roles,
//		      linkTo(methodOn(RoleController.class).getAllRoles()).withSelfRel());
//		  }
//	    @PostMapping("/roles/addrole")
//		  ResponseEntity<?> newRole(@RequestBody Role newRole) throws URISyntaxException {
	//
//		    Resource<Role> resource = assembler.toResource(roleService.addRole(newRole));
	//
//		    return ResponseEntity
//		      .created(new URI(resource.getId().expand().getHref()))
//		      .body(resource);
//		  }
//	    @GetMapping("/roles/{id}")
//		  Resource<Role> getRole(@PathVariable Long id) throws RoleNotFoundException {
	//
//	    	Role role = roleService.getRole(id)
//		      .orElseThrow(() -> new RoleNotFoundException(id));
	//
//		    return assembler.toResource(role);
//		  }
//		  @DeleteMapping("/roles/{id}")
//		  ResponseEntity<?> deleteRole(@PathVariable Long id) {
	//
//			  roleService.deleteRole(id);
	//
//		    return ResponseEntity.noContent().build();
//		  }
		
}
