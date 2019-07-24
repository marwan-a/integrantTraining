package com.javatpoint.dto;

import java.util.ArrayList;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;

@Mapper
public interface MyMapper {
	//User Mapping
	@Mappings({
        @Mapping(source = "user_id", target = "user_id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesCollectionToArrayList")
    })
	UserFront userToFront(UserRecord user);
	@Named("rolesCollectionToArrayList")
    default ArrayList<RoleDto> rolesCollectionToArrayList(Collection<Role> roles) {
        return rolesToDtos(roles);
    }
	@Mappings({
        @Mapping(source = "user_id", target = "user_id"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesArrayListToCollection")
    })
	UserRecord frontToUser(UserFront user);
	@Named("rolesArrayListToCollection")
    default Collection<Role> rolesArrayListToCollection(ArrayList<RoleDto> roles) {
        return dtosToRoles(roles);
    }
	
	ArrayList<UserFront> usersToFronts(Collection<UserRecord> users);
	Collection<UserRecord> frontsToUsers(ArrayList<UserFront> users);
	
	
	//Roles Mapping
	@Mappings({
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "role_id", target = "role_id"),
        @Mapping(source = "privileges", target = "privileges", qualifiedByName = "privilegesCollectionToArrayList")
    })
	RoleDto roleToDto(Role role);
	@Named("privilegesCollectionToArrayList")
    default ArrayList<PrivilegeDto> privilegesCollectionToArrayList(Collection<Privilege> privileges) {
        return privilegesToDtos(privileges);
    }
	@Mappings({
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "role_id", target = "role_id"),
        @Mapping(source = "privileges", target = "privileges",qualifiedByName = "privilegesArraylistToCollection")
    })
	Role dtoToRole(RoleDto roleDto);
	@Named("privilegesArraylistToCollection")
    default Collection<Privilege> privilegesArraylistToCollection(ArrayList<PrivilegeDto> privileges) {
		 if(privileges!=null)
			return dtosToPrivileges(privileges); 
		 else
			 return null;
    }
	ArrayList<RoleDto> rolesToDtos(Collection<Role> roles);
	Collection<Role> dtosToRoles(ArrayList<RoleDto> roles);
	
	//Privilege Mapping
	PrivilegeDto privelegeToDto(Privilege privilege);
	Privilege dtoToPrivilege(PrivilegeDto privilegeDto);
	ArrayList<PrivilegeDto> privilegesToDtos(Collection<Privilege> privileges);
	Collection<Privilege> dtosToPrivileges(ArrayList<PrivilegeDto> privileges);
}
