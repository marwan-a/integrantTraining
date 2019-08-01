package com.javatpoint.mappers;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import com.javatpoint.dto.RoleDto;
import com.javatpoint.models.Role;
import com.javatpoint.services.PrivilegeService;
import com.javatpoint.services.RoleService;


@Mapper(componentModel="spring")
public interface RoleMapper {
	@Mappings({
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "role_id", target = "role_id"),
        @Mapping(target="privileges",ignore = true)
    })
	RoleDto roleToDto(Role role, @Context PrivilegeService service);
	@AfterMapping
	   default void roleToDto( @MappingTarget RoleDto target, Role source, @Context PrivilegeService service) {
	        target.setPrivileges(service.getPrivilegeNames(source.getPrivileges()));
	   }
	@Mappings({
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "role_id", target = "role_id"),
        @Mapping(target="privileges",ignore = true)
    })
	Role dtoToRole(RoleDto roleDto, @Context RoleService service);
	@AfterMapping
	   default void dtoToRole( @MappingTarget Role target, RoleDto source, @Context RoleService service) {
			Role result=service.getRoleByName(source.getName());
	        target.setPrivileges(result.getPrivileges());
	        target.setUsers(result.getUsers());
	   }
	default ArrayList<RoleDto> rolesToDtos(Collection<Role> roles,@Context PrivilegeService service){
		ArrayList<RoleDto> rolesDto=new ArrayList<>();
		ArrayList<Role> objRoles=new ArrayList<>(roles);
		for(int i=0;i<objRoles.size();i++)
			rolesDto.add(roleToDto(objRoles.get(i), service));
		return rolesDto;
	}
	default Collection<Role> dtosToRoles(ArrayList<RoleDto> roles,@Context RoleService service){
		ArrayList<Role> objRoles=new ArrayList<>();
		for(int i=0;i<roles.size();i++)
			objRoles.add(dtoToRole(roles.get(i), service));
		return objRoles;
	}
}
