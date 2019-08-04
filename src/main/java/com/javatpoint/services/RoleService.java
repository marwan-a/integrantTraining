package com.javatpoint.services;

import java.util.ArrayList;
import java.util.Collection;
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
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}
	public void deleteRole(Long id) {
		roleRepository.deleteById(id);  
	}

	public ArrayList<String> getRolesNames(Collection<Role> roles) {
		if(roles==null)
			return null;
		ArrayList<String> strRoles=new ArrayList<>();
		ArrayList<Role> objRoles=new ArrayList<>(roles);
		for (int i = 0; i < objRoles.size(); i++) 
			strRoles.add(objRoles.get(i).getName());
		return strRoles;
	}
}
