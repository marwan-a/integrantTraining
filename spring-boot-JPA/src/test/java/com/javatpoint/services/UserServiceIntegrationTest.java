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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.javatpoint.dto.UserDto;
import com.javatpoint.exceptions.EmailExistsException;
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.PrivilegeRepository;
import com.javatpoint.repositories.RoleRepository;
import com.javatpoint.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
public class UserServiceIntegrationTest {
	@TestConfiguration
    static class UserServiceIntegrationTestContextConfiguration {
  
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }
 
    @Autowired
    private UserService userService;
 
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;
    @MockBean
    private PrivilegeRepository privilegeRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    public void setUp() {
        UserRecord user = new UserRecord();
        user.setEmail("user1@email.com");
        user.setName("User Test 1");
        user.setPassword("pass1234");
        UserRecord user2 = new UserRecord();
        user2.setEmail("user2@email.com");
        user2.setName("User Test 2");
        Mockito.when(userRepository.findByEmail(user.getEmail()))
          .thenReturn(user);
        Mockito.when(userRepository.save(user))
          .thenReturn(user);
        Mockito.when(passwordEncoder.encode(user.getPassword()))
        .thenReturn(user.getPassword());
        Mockito.when(userRepository.findAll())
          .thenReturn(Arrays.asList(user,user2));
    }
    @Test
    public void whenValidEmail_thenUserShouldBeFound() {
        String email = "user1@email.com";
        UserRecord found = userService.getUserByEmail(email);      
         assertThat(found.getEmail())
          .isEqualTo(email);
     }
    @Test
    public void whenAddUser_thenUserShouldBeAdded() {
        String name = "User Test 1";
        UserRecord user=new UserRecord();
        user.setName(name);
        user.setEmail("user1@email.com");
        user.setPassword("pass1234");
        UserRecord found = userService.addUser(user);
         assertThat(found.getName())
          .isEqualTo(name);
     }
    @Test
    public void whenDeleteUser_thenUserShouldBeDeleted() {
    	UserRecord u=new UserRecord();
        userService.deleteUser(u.getUser_id());
        verify(userRepository, times(1)).deleteById((u.getUser_id()));
     }
    @Test
    public void whenValidNames_thenUsersShouldBeFound() {
        String name = "User Test 1";
        String name2 = "User Test 2";
        java.util.List<UserRecord> users=userService.getAllUsers();     
         assertThat(users.get(0).getName())
          .isEqualTo(name);
         assertThat(users.get(1).getName())
          .isEqualTo(name2);
     }
    @Test
    public void whenRegisterUser_thenUserShouldBeRegistered() throws EmailExistsException {
        String name = "User Test 1";
        UserDto user=new UserDto();
        user.setName(name);
        user.setEmail("user3@email.com");
        user.setPassword("pass1234");
        UserRecord found=userService.registerNewUserAccount(user);
			assertThat(found.getName())
	          .isEqualTo(name);
     }
    @Test
    public void whenRegisterExistsingUser_thenUserShouldBeRegistered() {
        String name = "User Test 1";
        UserDto user=new UserDto();
        user.setName(name);
        user.setEmail("user1@email.com");
        user.setPassword("pass1234");
        user.setMatchingPassword("pass1234");
		try {
			UserRecord found = userService.registerNewUserAccount(user);
			assertThat(found.isEnabled())
	          .isEqualTo(true);
		} catch (EmailExistsException e) {
			assertThat(true)
	          .isEqualTo(true);
		}
     }
}
