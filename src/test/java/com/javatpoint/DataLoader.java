package com.javatpoint;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.ConfirmationTokenRepository;
import com.javatpoint.repositories.PrivilegeRepository;
import com.javatpoint.repositories.RoleRepository;
import com.javatpoint.repositories.UserRepository;
import com.javatpoint.verification.ConfirmationToken;

@Component
public class DataLoader implements ApplicationRunner {
	@Autowired
    private UserRepository userRepository;

	@Autowired
    private RoleRepository roleRepository;
	@Autowired
    private PrivilegeRepository privilegeRepository;
	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Privilege read=new Privilege("READ_PRIVILEGE");
		Privilege write =new Privilege("WRITE_PRIVILEGE");
		privilegeRepository.save(read); //id =1
		privilegeRepository.save(write); //id=2
		Role admin=new Role("ROLE_ADMIN");
		Role user= new Role("ROLE_USER");
		roleRepository.save(admin); //id=3
		roleRepository.save(user);	//id=4
		user.addPrivilege(read);
		admin.addPrivilege(read);
		admin.addPrivilege(write);
		UserRecord frodo=new UserRecord();
		frodo.setName("Frodo Baggins");
		frodo.setEmail("Frodo@mordor.com");
		frodo.setPassword(passwordEncoder.encode("ring1234"));
		frodo.setEnabled(true);
		UserRecord bilbo=new UserRecord();
		bilbo.setName("Bilbo Baggins");
		bilbo.setEmail("Bilbo@mordor.com");
		bilbo.setPassword(passwordEncoder.encode("ring1234"));
		bilbo.setEnabled(false);
		UserRecord sam=new UserRecord();
		sam.setName("Samwise");
		sam.setEmail("Sam@mordor.com");
		sam.setPassword(passwordEncoder.encode("ring1234"));
		sam.setEnabled(true);
		frodo.setRoles(Arrays.asList(admin));
		bilbo.setRoles(Arrays.asList(user));
		sam.setRoles(Arrays.asList(user));
		userRepository.save(frodo); //id=5
		userRepository.save(bilbo); //id=6
		userRepository.save(sam); 	//id=7
		ConfirmationToken token1=new ConfirmationToken(frodo);
		ConfirmationToken token2= new ConfirmationToken(bilbo);
		confirmationTokenRepository.save(token1); //id=8
		confirmationTokenRepository.save(token2); //id=9
		UserRecord temp=new UserRecord();
		temp.setName("temp");
		temp.setEmail("temp2@mordor.com");
		temp.setPassword(passwordEncoder.encode("ring1234"));
		temp.setEnabled(true);
		temp.setRoles(Arrays.asList(user));
		userRepository.save(temp); //id =10
	}
}
