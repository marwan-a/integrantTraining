package com.javatpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.javatpoint.dto.MyMapper;
import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.dto.RoleDto;
import com.javatpoint.dto.UserFront;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;

public class UserMapperTest {
	private MyMapper mapper
    = Mappers.getMapper(MyMapper.class);
  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
	  UserRecord userSource=new UserRecord();
	  userSource.setUser_id((long)8);
	  userSource.setEmail("test@email.com");
	  userSource.setName("Source Name");
	  userSource.setEnabled(true);
	  userSource.setPassword("pass");
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
      userSource.setRoles(Arrays.asList(simpleSource));
      UserFront destination = mapper.userToFront(userSource);
//      System.out.println(userSource);
//      System.out.println(destination);
      assertEquals(userSource.getName(), destination.getName());
  }
  @Test
  public void givenDestinationToSource_whenMaps_thenCorrect() {
	  UserFront destinationFront = new UserFront();
	  destinationFront.setEmail("test@email.com");
	  destinationFront.setUser_id((long) 9);
	  destinationFront.setName("DestinationName");
	  RoleDto destination=new RoleDto();
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
      destinationFront.setRoles(new ArrayList<>(Arrays.asList(destination)));
      UserRecord source = mapper.frontToUser(destinationFront);
//      System.out.println(source);
//      System.out.println(destinationFront);
      assertEquals(destinationFront.getName(), source.getName());
  }
}
