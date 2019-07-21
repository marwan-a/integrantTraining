package com.javatpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class SpringBootJpaApplication {
	//static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE_UNIT_NAME");
	//  static EntityManager em = emf.createEntityManager();
	  
	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaApplication.class, args);
//		em.getTransaction().begin();
//		Privilege readPrivilege = new Privilege("READ_PRIVILEGE");
//		privilegeRepository.save(readPrivilege);
//        Privilege  writePrivilege = new Privilege("WRITE_PRIVILEGE");
//        privilegeRepository.save(writePrivilege);
//        Role adminRole=createRoleIfNotFound("ROLE_ADMIN", Arrays.asList( readPrivilege, writePrivilege));
//        Role userRole=createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
//	    em.persist(adminRole);
//	    em.persist(userRole);
//	   
//	    em.flush();
	}
//	@Transactional
//	private static Role createRoleIfNotFound(
//	  String name, Collection<Privilege> privileges) {
//
//	    Role role = roleRepository.findByName(name);
//	    if (role == null) {
//	        role = new Role(name);
//	        role.setPrivileges(privileges);
//	        roleRepository.save(role);
//	    }
//	    return role;
//	}

}
/*@Autowired
private UserRepository userRepository;

@Autowired
private RoleRepository roleRepository;

@Autowired
private PrivilegeRepository privilegeRepository;

@Transactional
private Role createRoleIfNotFound(
  String name, Collection<Privilege> privileges) {

    Role role = roleRepository.findByName(name);
    if (role == null) {
        role = new Role(name);
        role.setPrivileges(privileges);
        roleRepository.save(role);
    }
    return role;
}
@SuppressWarnings("unused")
public void run(ApplicationArguments args) throws Exception {
	 	Privilege readPrivilege = new Privilege("READ_PRIVILEGE");
        privilegeRepository.save(readPrivilege);
        Privilege  writePrivilege = new Privilege("WRITE_PRIVILEGE");
        privilegeRepository.save(readPrivilege);
        Role adminRole=createRoleIfNotFound("ROLE_ADMIN", Arrays.asList( readPrivilege, writePrivilege));
        Role userRole=createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        UserRecord user = new UserRecord();
        user.setEmail("Frodo@mordor.com");
        user.setName("Frodo Baggins");
        user.setPassword("ring1234");
        user.setRoles(Arrays.asList(adminRole));
        user.setEnabled(true);
        userRepository.save(user);
        UserRecord user2 = new UserRecord();
        user2.setEmail("Bilbo@mordor.com");
        user2.setName("Bilbo Baggins");
        user2.setPassword("ring1234");
        userRepository.save(user2);
}*/
