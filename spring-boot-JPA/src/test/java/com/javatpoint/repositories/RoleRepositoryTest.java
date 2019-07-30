package com.javatpoint.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import com.javatpoint.TestConfiguration;
import com.javatpoint.models.Role;
import com.javatpoint.repositories.RoleRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		  classes = { TestConfiguration.class })
@Sql({"/test-schema.sql", "/test-data.sql"})
@TestMethodOrder(Alphanumeric.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class RoleRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private RoleRepository roleRepository;
	
	@Test
	public void whenFindByName_thenReturnRole() {
	    Role role = new Role("Test Case 1 Role");
	    entityManager.persist(role);
	    entityManager.flush();
	    Role found = roleRepository.findByName(role.getName());
	    assertThat(found.getName()).isEqualTo(role.getName());
	}
	@Test
	public void whenFindById_thenReturnRole() {
		Role role = new Role("Test Case 2 Role");
	    entityManager.persist(role);
	    entityManager.flush();
	    Optional<Role> found = roleRepository.findById(role.getRole_id());
	    if(found.isPresent())
	    	assertThat(found.get().getName()).isEqualTo(role.getName());
	    else
	    	assertThat(true).isEqualTo(false);
	}
	
}
