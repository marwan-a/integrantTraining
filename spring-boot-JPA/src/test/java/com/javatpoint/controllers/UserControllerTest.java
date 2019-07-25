package com.javatpoint.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.models.UserRecord;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;
	@BeforeEach()
	public void setup()
	{
		  mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenUser_whenGetUsers_thenOk() throws Exception
    {
    	mockMvc.perform(get("/users")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].name", is("Frodo Baggins")));
    }
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenUser_whenGetUser_thenOk() throws Exception
    {
    	mockMvc.perform(get("/users/5")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("name", is("Frodo Baggins")));
    }
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenAdmin_whenDeleteUser_thenOk() throws Exception
    {
    	this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/users/{id}", "6")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenAdmin_whenAddUser_thenOk() throws Exception 
    {
    	UserRecord user = new UserRecord();
        user.setEmail("temp@mordor.com");
        user.setName("Test Baggins");
        user.setEnabled(true);
        user.setPassword("ring1234");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(user);
    	this.mockMvc.perform(post("/admin/users/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("name", is("Test Baggins")));
    }
}
