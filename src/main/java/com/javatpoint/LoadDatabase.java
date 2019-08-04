package com.javatpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.UserRepository;

@Configuration
@EnableTransactionManagement
public class LoadDatabase {
	@Autowired
	private UserRepository userRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
	    System.out.println("hello world, I have just started up");
	    //adjust initial inserted data
	    UserRecord frodo= userRepository.findByEmail("Frodo@mordor.com");
	    if(!frodo.getPassword().substring(0, 7).equalsIgnoreCase("$2a$10$"))
	    {
		    frodo.setPassword(passwordEncoder.encode(frodo.getPassword()));
		    userRepository.save(frodo);
	    }
	    UserRecord bilbo= userRepository.findByEmail("Bilbo@mordor.com");
	    if(!bilbo.getPassword().substring(0, 7).equalsIgnoreCase("$2a$10$"))
	    {
		    bilbo.setPassword(passwordEncoder.encode(bilbo.getPassword()));
		    userRepository.save(bilbo);
	    }
	    UserRecord sam= userRepository.findByEmail("Sam@mordor.com");
	    if(!sam.getPassword().substring(0, 7).equalsIgnoreCase("$2a$10$"))
	    {
		    sam.setPassword(passwordEncoder.encode(sam.getPassword()));
		    userRepository.save(sam);
	    }

	}
}