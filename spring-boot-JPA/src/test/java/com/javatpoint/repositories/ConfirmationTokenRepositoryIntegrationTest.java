package com.javatpoint.repositories;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.ConfirmationTokenRepository;
import com.javatpoint.verification.ConfirmationToken;
@DataJpaTest
public class ConfirmationTokenRepositoryIntegrationTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Test
	public void whenFindByConfirmationToken_thenReturnConfirmationToken() {
		UserRecord user=new UserRecord();
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
