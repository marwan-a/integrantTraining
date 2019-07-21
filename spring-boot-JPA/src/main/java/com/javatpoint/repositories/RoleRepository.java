package com.javatpoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.javatpoint.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
	

}
