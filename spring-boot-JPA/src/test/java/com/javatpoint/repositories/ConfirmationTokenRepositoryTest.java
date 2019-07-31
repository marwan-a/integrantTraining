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
import com.javatpoint.repositories.ConfirmationTokenRepository;
import com.javatpoint.verification.ConfirmationToken;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(
		  classes = { TestConfiguration.class })
@Sql({"/test-schema.sql", "/test-data.sql"})
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class ConfirmationTokenRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Test
	public void whenFindByConfirmationToken_thenReturnConfirmationToken() {
		UserRecord user=new UserRecord();
		user.setName("test user");
		user.setEmail("test@email.com");
		user.setPassword("password");
	    entityManager.persist(user);
	    entityManager.flush();
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
	    entityManager.persist(confirmationToken);
	    entityManager.flush();
	    ConfirmationToken found = confirmationTokenRepository.findByConfirmationToken(confirmationToken.getConfirmationToken());
	    assertThat(found.getConfirmationToken()).isEqualTo(confirmationToken.getConfirmationToken());
	}
	@Test
	public void whenFindByUser_thenReturnConfirmationToken() {
		UserRecord user=new UserRecord();
		user.setName("test user 2");
		user.setEmail("test2@email.com");
		user.setPassword("password2");
	    entityManager.persist(user);
	    entityManager.flush();
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
	    entityManager.persist(confirmationToken);
	    entityManager.flush();
	    ConfirmationToken found = confirmationTokenRepository.findByUser(confirmationToken.getUser());
	    assertThat(found.getConfirmationToken()).isEqualTo(confirmationToken.getConfirmationToken());
	}
	@Test
	public void whenFindById_thenReturnConfirmationToken() {
		UserRecord user=new UserRecord();
		user.setName("test user 3");
		user.setEmail("test3@email.com");
		user.setPassword("password3");
	    entityManager.persist(user);
	    entityManager.flush();
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
	    entityManager.persist(confirmationToken);
	    entityManager.flush();
	    Optional<ConfirmationToken> found = confirmationTokenRepository.findById(confirmationToken.getTokenid());
	    if(found.isPresent())
	    	assertThat(found.get().getConfirmationToken()).isEqualTo(confirmationToken.getConfirmationToken());
	    else
	    	assertThat(true).isEqualTo(false);
	}
}
