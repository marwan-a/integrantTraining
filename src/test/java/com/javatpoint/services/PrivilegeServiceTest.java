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
import com.javatpoint.models.Privilege;
import com.javatpoint.repositories.PrivilegeRepository;

@ExtendWith(SpringExtension.class)
public class PrivilegeServiceTest {
	 @TestConfiguration
	    static class PrivilegeServiceIntegrationTestContextConfiguration {
	  
	        @Bean
	        public PrivilegeService privilegeService() {
	            return new PrivilegeService();
	        }
	    }
	 
	    @Autowired
	    private PrivilegeService privilegeService;
	 
	    @MockBean
	    private PrivilegeRepository privilegeRepository;
	    
	    @BeforeEach
	    public void setUp() {
	        Privilege privilege = new Privilege("Privilege Test 1");
	        Privilege privilege2 = new Privilege("Privilege Test 2");
	        Mockito.when(privilegeRepository.findByName(privilege.getName()))
	          .thenReturn(privilege);
	        Mockito.when(privilegeRepository.save(privilege))
	          .thenReturn(privilege);
	        Mockito.when(privilegeRepository.findAll())
	          .thenReturn(Arrays.asList(privilege,privilege2));
	    }
	    @Test
	    public void whenValidName_thenPrivilegeShouldBeFound() {
	        String name = "Privilege Test 1";
	        Privilege found = privilegeService.getPrivilegeByName(name);      
	         assertThat(found.getName())
	          .isEqualTo(name);
	     }
	    @Test
	    public void whenAddPrivilege_thenPrivilegeShouldBeAdded() {
	        String name = "Privilege Test 1";
	        Privilege found = privilegeService.addPrivilege(new Privilege(name));
	         assertThat(found.getName())
	          .isEqualTo(name);
	     }
	    @Test
	    public void whenDeletePrivilege_thenPrivilegeShouldBeDeleted() {
	        Privilege p=new Privilege();
	        privilegeService.deletePrivilege(p.getPrivilege_id());
	        verify(privilegeRepository, times(1)).deleteById((p.getPrivilege_id()));
	     }
	    @Test
	    public void whenValidNames_thenPrivilegesShouldBeFound() {
	        String name = "Privilege Test 1";
	        String name2 = "Privilege Test 2";
	        java.util.List<Privilege> privs=privilegeService.getAllPrivileges();     
	         assertThat(privs.get(0).getName())
	          .isEqualTo(name);
	         assertThat(privs.get(1).getName())
	          .isEqualTo(name2);
	     }
}
