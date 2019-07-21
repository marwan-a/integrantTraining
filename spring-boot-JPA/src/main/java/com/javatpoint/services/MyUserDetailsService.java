package com.javatpoint.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.javatpoint.models.Privilege;
import com.javatpoint.models.Role;
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.UserRepository;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
  
 
    public UserDetails loadUserByUsername(String email) 
    		  throws UsernameNotFoundException {
    		  
    		    boolean accountNonExpired = true;
    		    boolean credentialsNonExpired = true;
    		    boolean accountNonLocked = true;
    		    try {
    		        UserRecord user = userRepository.findByEmail(email);
    		        if (user == null) {
    		            throw new UsernameNotFoundException(
    		              "No user found with username: " + email);
    		        }
    		         
    		        return new org.springframework.security.core.userdetails.User(
    		          user.getEmail(), 
    		          user.getPassword().toLowerCase(), 
    		          user.isEnabled(), 
    		          accountNonExpired, 
    		          credentialsNonExpired, 
    		          accountNonLocked, 
    		          getAuthorities(user.getRoles()));
    		    } catch (Exception e) {
    		        throw new RuntimeException(e);
    		    }
    		}
 
    private Collection<? extends GrantedAuthority> getAuthorities(
    		Collection<Role> roles) {
  
        return getGrantedAuthorities(getPrivileges(roles));
    }
 
    private List<String> getPrivileges(Collection<Role> roles) {
  
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }
 
    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
    
}