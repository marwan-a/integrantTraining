package com.javatpoint.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.UserRepository;

@DataJpaTest
public class UserRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		UserRecord user=new UserRecord();
		user.setEmail("test@email.com");
	    entityManager.persist(user);
	    entityManager.flush();
	    UserRecord found = userRepository.findByEmail(user.getEmail());
	    assertThat(found.getEmail()).isEqualTo(user.getEmail());
	}
	@Test
	public void whenFindById_thenReturnUser() {
		UserRecord user=new UserRecord();
	    entityManager.persist(user);
	    entityManager.flush();
	    Optional<UserRecord> found = userRepository.findById(user.getUser_id());
	    if(found.isPresent())
	    	assertThat(found.get().getEmail()).isEqualTo(user.getEmail());
	    else
	    	assertThat(true).isEqualTo(false);
	}
}
