package com.javatpoint.models;  
import java.util.Collection;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.javatpoint.validations.FirstOrder;
import com.javatpoint.validations.SecondOrder;
import com.javatpoint.validations.ValidEmail;
import com.javatpoint.validations.ValidPassword;

import lombok.Data;  

@Entity 
@Data
@Table(name="users")
public class UserRecord {  
	private @Id  @GeneratedValue long user_id;  
	private @Column(name="name") @NotBlank(message = "Name is mandatory",groups = FirstOrder.class) String name;  
    private @Column(name="email",unique=true) @NotBlank(message = "Email is mandatory",groups = FirstOrder.class) @ValidEmail(groups = SecondOrder.class) String email; 
    private @Column(name="password") @NotBlank(groups = FirstOrder.class) @ValidPassword(groups = SecondOrder.class) String password;
    private @Column(name = "enabled") boolean enabled;
    @ManyToMany
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "user_id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "role_id")) 
    private Collection<Role> roles;
    public UserRecord() {
    	super();
    	this.enabled=false;
    }
}  