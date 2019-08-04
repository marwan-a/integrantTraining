package com.javatpoint.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

//import com.javatpoint.dto.MyMapper;
import com.javatpoint.dto.PrivilegeDto;
import com.javatpoint.models.Privilege;

public class PrivilegeMapperTest {
	private PrivilegeMapper mapper
    = Mappers.getMapper(PrivilegeMapper.class);
  @Test
  public void givenSourceToDestination_whenMaps_thenCorrect() {
      Privilege simpleSource = new Privilege();
      simpleSource.setName("SourceName");
      simpleSource.setPrivilege_id((long) 1);
      PrivilegeDto destination = mapper.privelegeToDto(simpleSource);
      assertEquals(simpleSource.getName(), destination.getName());
  }
  @Test
  public void givenDestinationToSource_whenMaps_thenCorrect() {
	  PrivilegeDto destination = new PrivilegeDto();
      destination.setName("DestinationName");
      destination.setPrivilege_id((long)2);
      Privilege source = mapper.dtoToPrivilege(destination);
      assertEquals(destination.getName(), source.getName());
  }
}
