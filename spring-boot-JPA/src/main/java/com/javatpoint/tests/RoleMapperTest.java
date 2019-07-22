package com.javatpoint.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.javatpoint.dto.MyMapper;
import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.dto.RoleDto;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;

public class RoleMapperTest {
	private MyMapper mapper
    = Mappers.getMapper(MyMapper.class);
  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
      Role simpleSource = new Role();
      simpleSource.setRole_id((long) 5);
      simpleSource.setName("SourceName");
      Privilege p=new Privilege();
      p.setPrivilege_id((long) 3);
      p.setName("Test Privilege");
      Privilege p2=new Privilege();
      p2.setPrivilege_id((long) 4);
      p2.setName("Test Privilege 2");
      simpleSource.addPrivilege(p);
      simpleSource.addPrivilege(p2);
      RoleDto destination = mapper.roleToDto(simpleSource);
      System.out.println(simpleSource);
      System.out.println(destination);
      assertEquals(simpleSource.getName(), destination.getName());
  }
  @Test
  public void givenDestinationToSource_whenMaps_thenCorrect() {
	  RoleDto destination = new RoleDto();
      destination.setName("DestinationName");
      PrivilegeDto p=new PrivilegeDto();
      p.setPrivilege_id((long) 1);
      p.setName("Test PrivilegeDto");
      PrivilegeDto p2=new PrivilegeDto();
      p2.setPrivilege_id((long) 2);
      p2.setName("Test PrivilegeDto 2");
      ArrayList<PrivilegeDto> privileges=new ArrayList<>();
      privileges.add(p);privileges.add(p2);
      destination.setPrivileges(privileges);
      Role source = mapper.dtoToRole(destination);
      System.out.println(source);
      System.out.println(destination);
      assertEquals(destination.getName(), source.getName());
  }
}
