package com.javatpoint.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.javatpoint.models.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
	Privilege findByName(String name);
}
