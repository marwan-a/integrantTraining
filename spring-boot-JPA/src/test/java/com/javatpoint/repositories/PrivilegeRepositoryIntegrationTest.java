package com.javatpoint.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import com.javatpoint.models.Privilege;
import com.javatpoint.repositories.PrivilegeRepository;

@DataJpaTest
public class PrivilegeRepositoryIntegrationTest {
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
