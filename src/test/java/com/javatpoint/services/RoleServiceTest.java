package com.javatpoint.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.javatpoint.models.Role;
import com.javatpoint.repositories.RoleRepository;

@ExtendWith(SpringExtension.class)
public class RoleServiceTest {
	 @TestConfiguration
	    static class RoleServiceIntegrationTestContextConfiguration {
	  
	        @Bean
	        public RoleService roleService() {
	            return new RoleService();
	        }
	    }
	 
	    @Autowired
	    private RoleService roleService;
	 
	    @MockBean
	    private RoleRepository roleRepository;
	    
	    @BeforeEach
	    public void setUp() {
	        Role role = new Role("Role Test 1");
	        Role role2 = new Role("Role Test 2");
	        Mockito.when(roleRepository.findByName(role.getName()))
	          .thenReturn(role);
	        Mockito.when(roleRepository.save(role))
	          .thenReturn(role);
	        Mockito.when(roleRepository.findAll())
	          .thenReturn(Arrays.asList(role,role2));
	    }
	    @Test
	    public void whenValidName_thenRoleShouldBeFound() {
	        String name = "Role Test 1";
	        Role found = roleService.getRoleByName(name);      
	         assertThat(found.getName())
	          .isEqualTo(name);
	     }
	    @Test
	    public void whenAddRole_thenRoleShouldBeAdded() {
	        String name = "Role Test 1";
	        Role found = roleService.addRole(new Role(name));
	         assertThat(found.getName())
	          .isEqualTo(name);
	     }
	    @Test
	    public void whenDeleteRole_thenRoleShouldBeDeleted() {
	        Role r=new Role();
	        roleService.deleteRole(r.getRole_id());
	        verify(roleRepository, times(1)).deleteById((r.getRole_id()));
	     }
	    @Test
	    public void whenValidNames_thenRolesShouldBeFound() {
	        String name = "Role Test 1";
	        String name2 = "Role Test 2";
	        java.util.List<Role> roles=roleService.getAllRoles();     
	         assertThat(roles.get(0).getName())
	          .isEqualTo(name);
	         assertThat(roles.get(1).getName())
	          .isEqualTo(name2);
	     }
	    
}
