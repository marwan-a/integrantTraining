package com.javatpoint.controllers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.javatpoint.TestConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
		  classes = { TestConfiguration.class })
@ActiveProfiles("test")
@ComponentScan(excludeFilters=@Filter(type = FilterType.REGEX, pattern="com.javatpoint.LoadDatabase*"))
public class SimpleControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
    private FilterChainProxy springSecurityFilterChain;
	private MockMvc mockMvc;
	@BeforeEach()
	public void setup()
	{
		  mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(this.springSecurityFilterChain).build();
	}
	@Test
    public void whenGetLoginPage_thenLogin() throws Exception
    {	
		String viewName=mockMvc.perform(get("/login")).andReturn().getModelAndView().getViewName();
   	 assertThat(viewName)
        .isEqualTo("login");
    }
	@Test
    public void whenSuccessfulAdminLogin_thenAdmin() throws Exception
    {	
		mockMvc
				.perform(formLogin("/login").user("Frodo@mordor.com").password("ring1234"))
				.andExpect(authenticated().withRoles("ADMIN"));
    }
	@Test
    public void whenSuccessfulUserLogin_thenUser() throws Exception
    {	
		mockMvc
				.perform(formLogin("/login").user("temp3@mordor.com").password("ring1234"))
				.andExpect(authenticated().withRoles("USER"));
    }
	@Test
    public void whenUnsuccessfulLogin_thenUnauthenticated() throws Exception
    {	
		mockMvc
				.perform(formLogin("/login").user("fake").password("fake"))
				.andExpect(unauthenticated());
    }
	@Test
    public void whenNotEnabledLogin_thenUnauthenticated() throws Exception
    {	
		mockMvc
				.perform(formLogin("/login").user("temp2@mordor.com").password("ring1234"))
				.andExpect(unauthenticated());
    }
	@Test
    public void whenAccessUserHomeUsingUser_thenOk() throws Exception
    {	
		ModelAndView modelAndView=mockMvc
				.perform(get("/user/home").with(user("user").password("user1234").roles("USER"))).andReturn().getModelAndView();
		assertThat(modelAndView.getViewName())
	      .isEqualTo("userhome");
    }
	@Test
    public void whenAccessUserHomeUsingAdmin_thenOk() throws Exception
    {	
		ModelAndView modelAndView=mockMvc
				.perform(get("/user/home").with(user("admin").password("admin1234").roles("ADMIN"))).andReturn().getModelAndView();
		assertThat(modelAndView.getViewName())
	      .isEqualTo("userhome");
    }
	@Test
    public void whenAccessAdminHomeUsingUser_thenNotOk() throws Exception
    {	
			mockMvc
				.perform(get("/admin/home").with(user("user").password("user1234").roles("USER")))
				.andExpect(status().isForbidden());
    }
	@Test
    public void whenAccessAdminHomeUsingAdmin_thenOk() throws Exception
    {	
		ModelAndView modelAndView=mockMvc
				.perform(get("/admin/home").with(user("admin").password("admin1234").roles("ADMIN"))).andReturn().getModelAndView();
		assertThat(modelAndView.getViewName())
	      .isEqualTo("adminhome");
    }
	
	@Test
    public void whenAccessAuthenticatedPageWithoutLoggingIn_thenLogin() throws Exception
    {	
			mockMvc
				.perform(get("/users").with(anonymous()))
				.andExpect(redirectedUrl("http://localhost/login"));
    }
}
