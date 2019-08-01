package com.javatpoint.mappers;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import com.javatpoint.dto.UserFront;
import com.javatpoint.models.UserRecord;
import com.javatpoint.services.RoleService;
import com.javatpoint.services.UserService;

@Mapper(componentModel="spring")
public interface UserMapper {
	
		@Mappings({
        @Mapping(source = "user_id", target = "user_id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "email", target = "email"),
		@Mapping(target="roles",ignore = true)
		})
	   UserFront userToFront( UserRecord source, @Context RoleService service);

	   @AfterMapping
	   default void userToFront( @MappingTarget UserFront target, UserRecord source, @Context RoleService service) {
	        target.setRoles(service.getRolesNames(source.getRoles()));
	   }
	   
		@Mappings({
			@Mapping(source = "user_id", target = "user_id"),
	        @Mapping(source = "name", target = "name"),
	        @Mapping(source = "email", target = "email"),
			@Mapping(target="roles",ignore = true)
			})
		UserRecord frontToUser( UserFront source, @Context UserService service);

		   @AfterMapping
		   default void frontToUser( @MappingTarget UserRecord target, UserFront source, @Context UserService service) {
			   UserRecord result=service.getUserByEmail(source.getEmail());
		        target.setEnabled(result.isEnabled());
		        target.setPassword(result.getPassword());
		        target.setRoles(result.getRoles());
		   }
		default ArrayList<UserFront> usersToFronts(Collection<UserRecord> users,@Context RoleService service){
			ArrayList<UserFront> usersFront=new ArrayList<>();
			ArrayList<UserRecord> objUsers=new ArrayList<>(users);
			for(int i=0;i<objUsers.size();i++)
				usersFront.add(userToFront(objUsers.get(i), service));
			return usersFront;
		}
		default Collection<UserRecord> frontsToUsers(ArrayList<UserFront> users,@Context UserService service){
			ArrayList<UserRecord> objUsers=new ArrayList<>();
			for(int i=0;i<users.size();i++)
				objUsers.add(frontToUser(users.get(i), service));
			return objUsers;
		}
}
