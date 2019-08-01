package com.javatpoint.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.javatpoint.dto.RoleDto;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.repositories.PrivilegeRepository;
import com.javatpoint.repositories.RoleRepository;
import com.javatpoint.services.PrivilegeService;
import com.javatpoint.services.RoleService;

@ExtendWith(SpringExtension.class)
public class RoleMapperTest {
	@TestConfiguration
    static class RoleMapperTestContextConfiguration {
		@Bean
        public RoleService roleService() {
            return new RoleService();
        }
        @Bean
        public PrivilegeService privilegeService() {
            return new PrivilegeService();
        }
    }
	@Autowired
    private PrivilegeService privilegeService;
	@Autowired
    private RoleService roleService;
    @MockBean
    private PrivilegeRepository privilegeRepository;
    @MockBean
    private RoleRepository roleRepository;
	private RoleMapper mapper
    = Mappers.getMapper(RoleMapper.class);
    @BeforeEach
    public void setUp() {
    	Role role=new Role();
    	role.setRole_id((long)6);
    	role.setName("DestinationName");
        Privilege privilege = new Privilege("Privilege 1");
        role.setPrivileges(Arrays.asList(privilege));
        Mockito.when(roleRepository.findByName(role.getName()))
          .thenReturn(role);
    }
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
      RoleDto destination = mapper.roleToDto(simpleSource, privilegeService);
      assertEquals(simpleSource.getName(), destination.getName());
  }
  @Test
  public void givenDestinationToSource_whenMaps_thenCorrect() {
	  RoleDto destination = new RoleDto();
	  destination.setRole_id((long)6);
      destination.setName("DestinationName");
      ArrayList<String> privileges=new ArrayList<>();
      privileges.add("Privilege 1");
      destination.setPrivileges(privileges);
      Role source = mapper.dtoToRole(destination, roleService);
      assertEquals(destination.getName(), source.getName());
  }
}
