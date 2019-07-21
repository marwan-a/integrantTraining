package com.javatpoint.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatpoint.models.Role;
import com.javatpoint.repositories.RoleRepository;

@Service
public class RoleService {
	@Autowired  
    private RoleRepository roleRepository; 
	public List<Role> getAllRoles() {
		List<Role>roleRecords = new ArrayList<>();  
		roleRepository.findAll().forEach(roleRecords::add);  
        return roleRecords;  
	}

	public Role addRole(Role newRole) {
	       return roleRepository.save(newRole);  
	}

	public Optional<Role> getRole(Long id) {
		return roleRepository.findById(id);
	}

	public void deleteRole(Long id) {
		roleRepository.deleteById(id);  
		
	}
}
