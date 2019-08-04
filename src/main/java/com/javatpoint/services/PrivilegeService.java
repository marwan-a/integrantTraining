package com.javatpoint.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.javatpoint.models.Privilege;
import com.javatpoint.repositories.PrivilegeRepository;
@Service
public class PrivilegeService {
	@Autowired  
    private PrivilegeRepository privilegeRepository; 
	public List<Privilege> getAllPrivileges() {
		List<Privilege>privilegeRecords = new ArrayList<>();  
		privilegeRepository.findAll().forEach(privilegeRecords::add);  
        return privilegeRecords;  
	}

	public Privilege addPrivilege(Privilege newPrivilege) {
	       return privilegeRepository.save(newPrivilege);  
	}

	public Optional<Privilege> getPrivilege(Long id) {
		return privilegeRepository.findById(id);
	}
	public Privilege getPrivilegeByName(String name) {
		return privilegeRepository.findByName(name);
	}

	public void deletePrivilege(Long id) {
		privilegeRepository.deleteById(id);  
		
	}

	public ArrayList<String> getPrivilegeNames(Collection<Privilege> privileges) {
		if(privileges==null)
			return null;
		ArrayList<String> strPrivileges=new ArrayList<>();
		ArrayList<Privilege> objPrivileges=new ArrayList<>(privileges);
		for (int i = 0; i < objPrivileges.size(); i++) 
			strPrivileges.add(objPrivileges.get(i).getName());
		return strPrivileges;
	}

}
