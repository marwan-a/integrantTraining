package com.javatpoint.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javatpoint.HibernateUtil;
import com.javatpoint.dto.UserDto;
import com.javatpoint.exceptions.EmailExistsException;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.PrivilegeRepository;
import com.javatpoint.repositories.RoleRepository;
import com.javatpoint.repositories.UserRepository;
import com.javatpoint.repositories.ConfirmationTokenRepository;
import com.javatpoint.verification.ConfirmationToken;

@Service  
public class UserService implements IUserService  {  
    @Autowired  
    private UserRepository userRepository; 
    @Autowired  
    private RoleRepository roleRepository; 
    @Autowired  
    private PrivilegeRepository privilegeRepository; 
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmationTokenRepository tokenRepository;
    public List<UserRecord> getAllUsers(){  
        List<UserRecord>userRecords = new ArrayList<>();  
        userRepository.findAll().forEach(userRecords::add);  
        return userRecords;  
    }  
    @Override
    public UserRecord getUser(String verificationToken) {
    	UserRecord user = tokenRepository.findByConfirmationToken(verificationToken).getUser();
        return user;
    } 
    public Optional<UserRecord> getUser(long id) {
    	Optional<UserRecord> user = userRepository.findById(id);
        return user;
    } 
    public UserRecord addUser(UserRecord userRecord){ 
       userRecord.setPassword(passwordEncoder.encode(userRecord.getPassword()));
       return userRepository.save(userRecord);  
    }  
    public void deleteUser(long id){  
        userRepository.deleteById(id);  
    }
    @Override
    public ConfirmationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByConfirmationToken(VerificationToken);
    }
     
    @Override
    public void saveRegisteredUser(UserRecord user) {
    	Session session=HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();
    	session.save(user);
        transaction.commit();
    }
     
    @Override
    public void createVerificationToken(UserRecord user, String token) {
        ConfirmationToken myToken = new ConfirmationToken(user);
        Session session=HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();
    	session.save(myToken);
        transaction.commit();
    }
    @Transactional
    @Override
    public UserRecord registerNewUserAccount(UserDto accountDto) 
      throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {  
            throw new EmailExistsException(
              "There is an account with that email adress: "
              +  accountDto.getEmail());
        }
        Session session=HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();
        UserRecord userRecord=new UserRecord();
        userRecord.setName(accountDto.getName());
        userRecord.setEmail(accountDto.getEmail());
        userRecord.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        Privilege readPrivilege=createPrivilegeIfNotFound("READ_PRIVILEGE");
        Role userRole=createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));
        userRecord.setRoles(Arrays.asList(userRole));
        session.save(userRecord);
        transaction.commit();
        session.close();
        //HibernateUtil.shutdown();
        return userRecord;
    }
    private boolean emailExist(String email) {
    	UserRecord user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
	@Transactional
	private Role createRoleIfNotFound(
	  String name, Collection<Privilege> privileges) {
		Session session=HibernateUtil.getSessionFactory().openSession();
    	Transaction transaction = session.beginTransaction();
	    Role role = roleRepository.findByName(name);
	    if (role == null) {
	    	System.out.println("creating role user service");
	        role = new Role(name);
	        role.setPrivileges(privileges);
	        //roleRepository.save(role);
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
	    	System.out.println("creating privilege user service");
            privilege = new Privilege(name);
            //privilegeRepository.save(privilege);
            session.save(privilege);
            transaction.commit();
        }
        session.close();
        return privilege;
    }


}  
