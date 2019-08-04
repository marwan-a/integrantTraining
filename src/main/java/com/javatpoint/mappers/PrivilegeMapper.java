package com.javatpoint.mappers;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.Mapper;

import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.models.Privilege;

@Mapper(componentModel="spring")
public interface PrivilegeMapper {
	PrivilegeDto privelegeToDto(Privilege privilege);
	Privilege dtoToPrivilege(PrivilegeDto privilegeDto);
	ArrayList<PrivilegeDto> privilegesToDtos(Collection<Privilege> privileges);
	Collection<Privilege> dtosToPrivileges(ArrayList<PrivilegeDto> privileges);
}
