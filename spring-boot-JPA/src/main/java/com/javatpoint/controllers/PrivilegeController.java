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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.exceptions.PrivilegeNotFoundException;
import com.javatpoint.models.Privilege;
import com.javatpoint.services.PrivilegeService;

@RestController
public class PrivilegeController {
	@Autowired  
    private PrivilegeService privilegeService; 
	private  PrivilegeResourceAssembler assembler; 
	PrivilegeController(PrivilegeService privilegeService,
			PrivilegeResourceAssembler assembler) {

   this.privilegeService = privilegeService;
   this.assembler = assembler;
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

	  @GetMapping("/privileges")
	  ArrayList<PrivilegeDto> getAllPrivileges() {
		List<Privilege> privileges=privilegeService.getAllPrivileges();
		ArrayList<PrivilegeDto> privilegesDto=new ArrayList<>();
		for (int i = 0; i < privileges.size(); i++) {
			PrivilegeDto tempDto=new PrivilegeDto();
			tempDto.setName(privileges.get(i).getName());
			privilegesDto.add(tempDto);
		}
	    return privilegesDto;
	  }

	  @PostMapping("/admin/privileges/addprivilege")
	  Privilege newPrivilege(@RequestBody Privilege newPrivilege) {
		  System.out.println("in add privilege");
	    return privilegeService.addPrivilege(newPrivilege);
	  }

	  // Single item

	  @GetMapping("/privileges/{id}")
	  PrivilegeDto getPrivilege(@PathVariable Long id) throws PrivilegeNotFoundException {
		  Privilege priv=privilegeService.getPrivilege(id)
			      .orElseThrow(() -> new PrivilegeNotFoundException(id));
		  PrivilegeDto pd=new PrivilegeDto();
		  pd.setName(priv.getName());
		  return pd;
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
		  System.out.println("in delete");
	    privilegeService.deletePrivilege(id);
	  }
}
