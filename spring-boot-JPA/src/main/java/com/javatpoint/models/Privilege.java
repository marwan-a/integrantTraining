package com.javatpoint.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Data
public class Privilege {

	@Id
    @GeneratedValue
	@Column(nullable = false, unique = true)
    private Long privilege_id;
	@Column(nullable = false, unique = true)
    private String name;
    public Privilege(String name) {
    	this.name=name;
    	roles = new ArrayList<Role>();   	
    }
    public Privilege() {
    	roles = new ArrayList<Role>();
    }   
    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;
    
}