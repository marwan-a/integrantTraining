package com.javatpoint.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javatpoint.dto.MyMapper;
import com.javatpoint.dto.UserFront;
import com.javatpoint.exceptions.UserNotFoundException;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;
import com.javatpoint.services.UserService;
import com.javatpoint.validations.ValidationSequence;


@RestController
public class UserController {  
	@Autowired  
    private UserService userService; 
	private  UserResourceAssembler assembler; 
	private MyMapper mapper
    = Mappers.getMapper(MyMapper.class);
	UserController(UserService userService,
			UserResourceAssembler assembler) {

   this.userService = userService;
   this.assembler = assembler;
 }

	//all users with resource hateaos
	@GetMapping("/users")
	  ArrayList<UserFront> getAllUsers() {
		return mapper.usersToFronts(userService.getAllUsers());
	  }
//    @GetMapping("/users")
//	  Resources<Resource<UserRecord>> getAllUsers() {
//
//	    List<Resource<UserRecord>> users = userService.getAllUsers().stream()
//	      .map(assembler::toResource)
//	      .collect(Collectors.toList());
//
//	    return new Resources<>(users,
//	      linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
//	  }
    @PostMapping("/admin/users/adduser")
    UserFront newUser(@Validated(ValidationSequence.class) @RequestBody UserRecord newUser) {
	    return mapper.userToFront(userService.addUser(newUser));
	  }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
    @GetMapping("/users/{id}")
	  UserFront getUser(@PathVariable Long id) throws UserNotFoundException {
	    return mapper.userToFront(userService.getUser(id)
	      .orElseThrow(() -> new UserNotFoundException(id)));
	  }
//    @GetMapping("/users/{id}")
//	  Resource<UserRecord> getUser(@PathVariable Long id) throws UserNotFoundException {
//
//    	UserRecord user = userService.getUser(id)
//	      .orElseThrow(() -> new UserNotFoundException(id));
//
//	    return assembler.toResource(user);
//	  }
//    @PutMapping("/users/{id}")
//	  ResponseEntity<?> replaceUser(@RequestBody UserRecord newUser, @PathVariable Long id) throws URISyntaxException {
//
//    	UserRecord updatedUser = userService.getUser(id)
//	      .map(user -> {
//	        user.setName(newUser.getName());
//	        user.setEmail(newUser.getEmail());
//	        return userService.addUser(user);
//	      })
//	      .orElseGet(() -> {
//	    	  newUser.setUser_id(id);
//	        return userService.addUser(newUser);
//	      });
//
//	    Resource<UserRecord> resource = assembler.toResource(updatedUser);
//
//	    return ResponseEntity
//	      .created(new URI(resource.getId().expand().getHref()))
//	      .body(resource);
//	  }

	  @DeleteMapping("/users/{id}")
	  ResponseEntity<?> deleteUser(@PathVariable Long id) {
	    userService.deleteUser(id);
	    return ResponseEntity.noContent().build();
	  }
}  