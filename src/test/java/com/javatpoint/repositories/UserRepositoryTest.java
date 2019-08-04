package com.javatpoint.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
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
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.UserRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		  classes = { TestConfiguration.class })
@Sql({"/test-schema.sql", "/test-data.sql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void whenFindByEmail_thenReturnUser() {
		UserRecord user=new UserRecord();
		user.setName("test user");
		user.setEmail("test@email.com");
		user.setPassword("password");
	    entityManager.persist(user);
	    entityManager.flush();
	    UserRecord found = userRepository.findByEmail(user.getEmail());
	    assertThat(found.getEmail()).isEqualTo(user.getEmail());
	}
	@Test
	public void whenFindById_thenReturnUser() {
		UserRecord user=new UserRecord();
		user.setName("test user 2");
		user.setEmail("test2@email.com");
		user.setPassword("password2");
	    entityManager.persist(user);
	    entityManager.flush();
	    Optional<UserRecord> found = userRepository.findById(user.getUser_id());
	    if(found.isPresent())
	    	assertThat(found.get().getEmail()).isEqualTo(user.getEmail());
	    else
	    	assertThat(true).isEqualTo(false);
	}
}
