package com.javatpoint.models;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="roles")
public class Role {
  
   
	@Id
    @GeneratedValue
	@Column(nullable = false, unique = true)
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

//    @ManyToMany(mappedBy = "roles",cascade = CascadeType.ALL)
    @ManyToMany(fetch = FetchType.LAZY,
    cascade =
    {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    },
    targetEntity = UserRecord.class)
@JoinTable(name = "users_roles",
    inverseJoinColumns = @JoinColumn(name = "user_id",
            nullable = false,
            updatable = false),
    joinColumns = @JoinColumn(name = "role_id",
            nullable = false,
            updatable = false),
    foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT),
    inverseForeignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Collection<UserRecord> users;
 
    @ManyToMany(cascade = CascadeType.ALL)
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