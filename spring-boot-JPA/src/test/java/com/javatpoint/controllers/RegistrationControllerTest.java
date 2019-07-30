package com.javatpoint.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hibernate.Hibernate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.javatpoint.dto.UserDto;
import com.javatpoint.models.UserRecord;
import com.javatpoint.services.ConfirmationTokenService;
import com.javatpoint.services.UserService;
import com.javatpoint.verification.ConfirmationToken;

//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RegistrationControllerTest {
//	@Autowired
//	private WebApplicationContext webApplicationContext;
//	private MockMvc mockMvc;
//	@Autowired
//	private ConfirmationTokenService confirmationTokenService;
//	@Autowired
//	private UserService userService;
//	@BeforeEach()
//	public void setup()
//	{
//		  mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
////    @Test
////    public void whenGetRegistrationForm_thenOk() throws Exception
////    {
////    	String viewName=mockMvc.perform(get("/user/registration")).andReturn().getModelAndView().getViewName();
////    	 assertThat(viewName)
////         .isEqualTo("registration");
////    }
////    @Test
////    public void whenPostSuccessfulRegistrationForm_thenOk() throws Exception
////    {
////      UserDto user = new UserDto();
////      user.setEmail("marwanspringtest@gmail.com");
////      user.setName("Test Baggins");
////      user.setPassword("ring1234");
////      user.setMatchingPassword("ring1234");
////      ModelAndView modelAndView=mockMvc.perform(post("/user/registration").flashAttr("user", user)).andReturn().getModelAndView();
////      assertThat(modelAndView.getViewName())
////      .isEqualTo("successfulRegisteration");
////    }
////    @Test
////    public void whenPostAlreadyRegisteredRegistrationForm_thenOk() throws Exception
////    {	
////    	//Forod@mordor.com is already registered
////      UserDto user = new UserDto();
////      user.setEmail("Frodo@mordor.com");
////      user.setName("Test Baggins");
////      user.setPassword("ring1234");
////      user.setMatchingPassword("ring1234");
////      this.mockMvc.perform( 
////    		  post("/user/registration")
////    		  .flashAttr("user", user)
////    		).andExpect(model()
////    	          .attributeHasFieldErrors("user", "email"))
////    	             .andExpect( status().isOk() );
////    }
////    @Test
////    public void whenPostUnsuccessfulRegistrationForm_thenOk() throws Exception
////    {	
////    	// Name is Blank
////      UserDto user = new UserDto();
////      user.setEmail("Frodo@mordor.com");
////      user.setPassword("ring1234");
////      user.setMatchingPassword("ring1234");
////      this.mockMvc.perform( 
////    		  post("/user/registration")
////    		  .flashAttr("user", user)
////    		).andExpect(model()
////    	          .attributeHasFieldErrors("user", "name"))
////    	             .andExpect( status().isOk() );
////    }
//    @Test
//    public void whenConfirmAccountInvalidToken_thenInvalidToken() throws Exception
//    {	
//      	ModelAndView modelAndView=this.mockMvc.perform( 
//      			  post("/confirm-account")
//      				.param("token", "faketoken")
//      			).andReturn().getModelAndView();
//      	assertThat(modelAndView.getViewName())
//          .isEqualTo("invalidToken");
//    }
//    @Test
//    @Transactional
//    @Rollback
//    @WithUserDetails("Frodo@mordor.com")
//    public void whenConfirmAccountNoAccount_thenNoAccount() throws Exception
//    {	
//    	ConfirmationToken conn=confirmationTokenService.getConfirmationTokenById((long) 8).get();
//    	String token=conn.getConfirmationToken();
//    	//delete user assigned to this token
//    	userService.deleteUser((long) 6);
//      	ModelAndView modelAndView=this.mockMvc.perform( 
//      			  post("/confirm-account")
//      				.param("token", token)
//      			).andReturn().getModelAndView();
//      	assertThat(modelAndView.getViewName())
//          .isEqualTo("noAccount");
//    }
//    //confirm account 5 cases testing
//    //resend token => should change controller method to handle exceptions like sending the token

}
