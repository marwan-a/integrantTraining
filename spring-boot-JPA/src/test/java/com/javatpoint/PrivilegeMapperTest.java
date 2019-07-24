package com.javatpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.javatpoint.dto.MyMapper;
import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.models.Privilege;

public class PrivilegeMapperTest {
	private MyMapper mapper
    = Mappers.getMapper(MyMapper.class);
  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
      Privilege simpleSource = new Privilege();
      simpleSource.setName("SourceName");
      simpleSource.setPrivilege_id((long) 1);
      PrivilegeDto destination = mapper.privelegeToDto(simpleSource);
//      System.out.println(simpleSource);
//      System.out.println(destination);
      assertEquals(simpleSource.getName(), destination.getName());
  }
  @Test
  public void givenDestinationToSource_whenMaps_thenCorrect() {
	  PrivilegeDto destination = new PrivilegeDto();
      destination.setName("DestinationName");
      destination.setPrivilege_id((long)2);
      Privilege source = mapper.dtoToPrivilege(destination);
//      System.out.println(source);
//      System.out.println(destination);
      assertEquals(destination.getName(), source.getName());
  }
}
