package com.javatpoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatpoint.models.UserRecord;
import com.javatpoint.verification.ConfirmationToken;

public interface ConfirmationTokenRepository 
extends JpaRepository<ConfirmationToken, Long> {

  ConfirmationToken findByConfirmationToken(String token);

  ConfirmationToken findByUser(UserRecord user);
}