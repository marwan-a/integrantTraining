package com.javatpoint.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.exceptions.PrivilegeNotFoundException;
import com.javatpoint.mappers.PrivilegeMapper;
import com.javatpoint.models.Privilege;
import com.javatpoint.services.PrivilegeService;

@SuppressWarnings("unused")
@RestController
public class PrivilegeController {
	@Autowired  
    private PrivilegeService privilegeService; 
	private  PrivilegeResourceAssembler assembler; 
	private PrivilegeMapper mapper
    = Mappers.getMapper(PrivilegeMapper.class);
	PrivilegeController(PrivilegeService privilegeService,
			PrivilegeResourceAssembler assembler) {

   this.privilegeService = privilegeService;
   this.assembler = assembler;
 }


	  @GetMapping("/privileges")
	  ArrayList<PrivilegeDto> getAllPrivileges() {
	    return mapper.privilegesToDtos(privilegeService.getAllPrivileges());
	  }

	  @PostMapping("/admin/privileges/addprivilege")
	  PrivilegeDto newPrivilege(@RequestBody PrivilegeDto newPrivilege) {
	    return mapper.privelegeToDto(privilegeService.addPrivilege(mapper.dtoToPrivilege(newPrivilege)));
	  }
	  
	  @GetMapping("/privileges/{id}")
	  PrivilegeDto getPrivilege(@PathVariable Long id) throws PrivilegeNotFoundException {
		  return mapper.privelegeToDto(privilegeService.getPrivilege(id)
			      .orElseThrow(() -> new PrivilegeNotFoundException(id)));
	  }

//	  @PutMapping("/privileges/{id}")
//	  Privilege replaceEmployee(@RequestBody Privilege newEmployee, @PathVariable Long id) {
//
//	    return repository.findById(id)
//	      .map(employee -> {
//	        employee.setName(newEmployee.getName());
//	        employee.setRole(newEmployee.getRole());
//	        return repository.save(employee);
//	      })
//	      .orElseGet(() -> {
//	        newEmployee.setId(id);
//	        return repository.save(newEmployee);
//	      });
//	  }

	  @DeleteMapping("/admin/privileges/{id}")
	  void deletePrivilege(@PathVariable Long id) {
	    privilegeService.deletePrivilege(id);
	  }
		//all privileges with resource hateaos
//    @GetMapping("/privileges")
//	  Resources<Resource<Privilege>> getAllPrivileges() {
//
//	    List<Resource<Privilege>> privileges = privilegeService.getAllPrivileges().stream()
//	      .map(assembler::toResource)
//	      .collect(Collectors.toList());
//
//	    return new Resources<>(privileges,
//	      linkTo(methodOn(PrivilegeController.class).getAllPrivileges()).withSelfRel());
//	  }
//    @PostMapping("/privileges/addprivilege")
//	  ResponseEntity<?> newPrivilege(@RequestBody Privilege newPrivilege) throws URISyntaxException {
//
//	    Resource<Privilege> resource = assembler.toResource(privilegeService.addPrivilege(newPrivilege));
//
//	    return ResponseEntity
//	      .created(new URI(resource.getId().expand().getHref()))
//	      .body(resource);
//	  }
//    @GetMapping("/privileges/{id}")
//	  Resource<Privilege> getPrivilege(@PathVariable Long id) throws PrivilegeNotFoundException {
//
//    	Privilege privilege = privilegeService.getPrivilege(id)
//	      .orElseThrow(() -> new PrivilegeNotFoundException(id));
//		
//	    return assembler.toResource(privilege);
//	  }
//	  @DeleteMapping("/privileges/{id}")
//	  ResponseEntity<?> deletePrivilege(@PathVariable Long id) {
//
//		  privilegeService.deletePrivilege(id);
//
//	    return ResponseEntity.noContent().build();
//	  }
	// Aggregate root

}
