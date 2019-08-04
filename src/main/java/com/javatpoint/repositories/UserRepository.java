package com.javatpoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatpoint.models.UserRecord;

public interface UserRepository extends JpaRepository<UserRecord, Long> {

	public UserRecord findByEmail(String email);
}
