package com.javatpoint.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.ConfirmationTokenRepository;
import com.javatpoint.verification.ConfirmationToken;
@ExtendWith(SpringExtension.class)

public class ConfirmationTokenServiceTest {
	@TestConfiguration
    static class ConfirmationTokenServiceIntegrationTestContextConfiguration {
  
        @Bean
        public ConfirmationTokenService confirmationTokenService() {
            return new ConfirmationTokenService();
        }
    }
 
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
 
    @MockBean
    private ConfirmationTokenRepository confirmationTokenRepository;
    
    @BeforeEach
    public void setUp() {
    	UserRecord user=new UserRecord();
    	user.setEmail("user1@email.com");
        user.setName("User Test 1");
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationToken.setConfirmationToken("ConfirmationToken Test 1");
        Mockito.when(confirmationTokenRepository.findByConfirmationToken(confirmationToken.getConfirmationToken()))
          .thenReturn(confirmationToken);
        Mockito.when(confirmationTokenRepository.save((ConfirmationToken)notNull()))
          .thenReturn(confirmationToken);
    }
    @Test
    public void whenValidConfirmationToken_thenConfirmationTokenShouldBeFound() {
        String name = "ConfirmationToken Test 1";
        ConfirmationToken found = confirmationTokenService.getConfirmationToken(name);      
         assertThat(found.getConfirmationToken())
          .isEqualTo(name);
     }
    @Test
    public void whenAddConfirmationToken_thenConfirmationTokenShouldBeAdded() {
        String name = "ConfirmationToken Test 1";
        UserRecord user=new UserRecord();
    	user.setEmail("user1@email.com");
        user.setName("User Test 1");
        ConfirmationToken found = confirmationTokenService.createConfirmationToken(user);
         assertThat(found.getConfirmationToken())
          .isEqualTo(name);
     }  
}
