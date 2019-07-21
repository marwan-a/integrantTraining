package com.javatpoint.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Role {
  
   
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long role_id;
	@Column(nullable = false, unique = true)
    private String name;
    
    public Role(String name) {
    	this.name=name;
    	privileges = new ArrayList<Privilege>();
    }
    public Role() {
    	privileges = new ArrayList<Privilege>();
    }

    @ManyToMany(mappedBy = "roles")
    private Collection<UserRecord> users;
 
    @ManyToMany
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "role_id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "privilege_id"))
    private Collection<Privilege> privileges; 
    
    public void addPrivilege(Privilege privilege) {
        if (!getPrivileges().contains(privilege)) {
        	getPrivileges().add(privilege);
        }
      }
}