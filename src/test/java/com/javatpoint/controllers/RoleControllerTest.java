package com.javatpoint.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatpoint.TestConfiguration;
import com.javatpoint.dto.RoleDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
		  classes = { TestConfiguration.class })
//@Sql({"/test-schema.sql", "/test-data.sql"})
@ActiveProfiles("test")
@ComponentScan(excludeFilters=@Filter(type = FilterType.REGEX, pattern="com.javatpoint.LoadDatabase*"))
public class RoleControllerTest {
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
    public void givenUser_whenGetRoles_thenOk() throws Exception
    {
    	mockMvc.perform(get("/roles")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].name", is("ROLE_ADMIN")));
    }
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenUser_whenGetRole_thenOk() throws Exception
    {
    	mockMvc.perform(get("/roles/3")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("name", is("ROLE_ADMIN")));
    }
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenAdmin_whenDeleteRole_thenOk() throws Exception
    {
    	this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/roles/{id}", "11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenAdmin_whenAddRole_thenOk() throws Exception 
    {
    	RoleDto role = new RoleDto();
    	role.setName("Test2");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(role);
    	this.mockMvc.perform(post("/admin/roles/addrole")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("name", is("Test2")));
    }
}
