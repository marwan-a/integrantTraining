package com.javatpoint.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.javatpoint.TestConfiguration;
import com.javatpoint.models.Privilege;
import com.javatpoint.repositories.PrivilegeRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		  classes = { TestConfiguration.class })
@Sql({"/test-schema.sql", "/test-data.sql"})
@TestMethodOrder(Alphanumeric.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PrivilegeRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Test
	public void whenFindByName_thenReturnPrivilege() {
	    Privilege privilege = new Privilege("Test Case 1 Privilege");
	    entityManager.persist(privilege);
	    entityManager.flush();
	    Privilege found = privilegeRepository.findByName(privilege.getName());
	    assertThat(found.getName()).isEqualTo(privilege.getName());
	}
	@Test
	public void whenFindById_thenReturnPrivilege() {
	    Privilege privilege = new Privilege("Test Case 2 Privilege");
	    entityManager.persist(privilege);
	    entityManager.flush();
	    Optional<Privilege> found = privilegeRepository.findById(privilege.getPrivilege_id());
	    if(found.isPresent())
	    	assertThat(found.get().getName()).isEqualTo(privilege.getName());
	    else
	    	assertThat(true).isEqualTo(false);
	}
}
