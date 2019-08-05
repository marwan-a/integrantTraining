package com.javatpoint.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
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
import com.javatpoint.dto.PrivilegeDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(
		  classes = { TestConfiguration.class })
//@Sql({"/test-schema.sql", "/test-data.sql"})
@ActiveProfiles("test")
@ComponentScan(excludeFilters=@Filter(type = FilterType.REGEX, pattern="com.javatpoint.LoadDatabase*"))
public class PrivilegeControllerTest {
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
    public void givenUser_whenGetPrivileges_thenOk() throws Exception
    {
    	mockMvc.perform(get("/privileges")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$[0].name", is("READ_PRIVILEGE")));
    }
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenUser_whenGetPrivilege_thenOk() throws Exception
    {
    	mockMvc.perform(get("/privileges/1")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("name", is("READ_PRIVILEGE")));
    }
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenAdmin_whenDeletePrivilege_thenOk() throws Exception
    {
    	this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/admin/privileges/{id}", "2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    @WithUserDetails("Frodo@mordor.com")
    public void givenAdmin_whenAddPrivilege_thenOk() throws Exception 
    {
    	PrivilegeDto priv = new PrivilegeDto();
    	priv.setName("Test");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(priv);
    	this.mockMvc.perform(post("/admin/privileges/addprivilege")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .characterEncoding("utf-8"))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("name", is("Test")));
    }
}
