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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.javatpoint.dto.UserFront;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.PrivilegeRepository;
import com.javatpoint.repositories.RoleRepository;
import com.javatpoint.repositories.UserRepository;
import com.javatpoint.services.RoleService;
import com.javatpoint.services.UserService;
@ExtendWith(SpringExtension.class)
public class UserMapperTest {
	@TestConfiguration
    static class UserMapperTestContextConfiguration {
  
        @Bean
        public RoleService roleService() {
            return new RoleService();
        }
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }
 
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean 
    private UserRepository userRepository; 
    @MockBean  
    private PrivilegeRepository privilegeRepository; 
    @MockBean
    private PasswordEncoder passwordEncoder;
	private UserMapper mapper=Mappers.getMapper(UserMapper.class);
    @BeforeEach
    public void setUp() {
        Role role = new Role("ROLE_USER");
        UserRecord user=new UserRecord();
        user.setEmail("test@email.com");
        user.setUser_id((long) 9);
        user.setName("DestinationName");
        user.setPassword("ring1234");
        user.setRoles(new ArrayList<>(Arrays.asList(role)));
        Mockito.when(roleRepository.findByName(role.getName()))
          .thenReturn(role);
        Mockito.when(userRepository.findByEmail(user.getEmail()))
        .thenReturn(user);
    }
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
	      UserFront destination = mapper.userToFront(userSource, roleService);
	      System.out.println(userSource);
	      System.out.println(destination);
	      assertEquals(userSource.getName(), destination.getName());
	  }
	@Test
	public void givenDestinationToSource_whenMaps_thenCorrect() {
		  UserFront destinationFront = new UserFront();
		  destinationFront.setEmail("test@email.com");
		  destinationFront.setUser_id((long) 9);
		  destinationFront.setName("DestinationName");
		  destinationFront.setRoles(new ArrayList<>(Arrays.asList("ROLE_USER")));
	    UserRecord source = mapper.frontToUser(destinationFront, userService);
	    System.out.println(source);
	    System.out.println(destinationFront);
	    assertEquals(destinationFront.getName(), source.getName());
	}
}
