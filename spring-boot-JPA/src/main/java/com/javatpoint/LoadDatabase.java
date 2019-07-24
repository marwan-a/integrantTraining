package com.javatpoint;

import java.util.Arrays;
import java.util.Collection;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;
import com.javatpoint.models.Privilege;
import com.javatpoint.repositories.PrivilegeRepository;
import com.javatpoint.repositories.RoleRepository;
import com.javatpoint.repositories.UserRepository;

@Configuration
@EnableTransactionManagement
public class LoadDatabase {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Bean
	  CommandLineRunner initDatabase(RoleRepository roleRepository, PrivilegeRepository privilegeRepository, UserRepository userRepository) {
	    return args -> {
	    	Session session=HibernateUtil.getSessionFactory().openSession();
	    	Transaction transaction = session.beginTransaction();
	    	Privilege readPrivilege=createPrivilegeIfNotFound("READ_PRIVILEGE");
	    	Privilege writePrivilege=createPrivilegeIfNotFound("WRITE_PRIVILEGE");
	    	Role adminRole = createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(readPrivilege, writePrivilege));
	    	Role userRole = createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
	        UserRecord user = new UserRecord();
	        user.setEmail("Frodo@mordor.com");
	        user.setName("Frodo Baggins");
	        user.setPassword("ring1234");
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        user.setRoles(Arrays.asList(adminRole));
	    	session.save(user);	
	        UserRecord user2 = new UserRecord();
	        user2.setEmail("Bilbo@mordor.com");
	        user2.setName("Bilbo Baggins");
	        user2.setPassword("ring1234");
	        user2.setPassword(passwordEncoder.encode(user2.getPassword()));
	        user2.setRoles(Arrays.asList(userRole));
	    	session.save(user2);
	        transaction.commit();
	        session.close();
	    };
	  }
	@Transactional
	private Role createRoleIfNotFound(
	  String name, Collection<Privilege> privileges) {
		Session session=HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();
	    Role role = roleRepository.findByName(name);
	    if (role == null) {
	        role = new Role(name);
	        role.setPrivileges(privileges);
	        session.save(role);
		    transaction.commit();

	    }
        session.close();
	    return role;
	}
	@Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
		Session session=HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            session.save(privilege);
    	    transaction.commit();

        }
        session.close();
        return privilege;
    }
	
}