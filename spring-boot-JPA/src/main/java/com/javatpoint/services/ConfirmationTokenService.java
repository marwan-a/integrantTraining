package com.javatpoint.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.ConfirmationTokenRepository;
import com.javatpoint.verification.ConfirmationToken;

@Service
public class ConfirmationTokenService {
	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

	public ConfirmationToken createConfirmationToken(UserRecord registered) {
		return confirmationTokenRepository.save(new ConfirmationToken(registered));
	}

	public ConfirmationToken getConfirmationToken(String confirmationToken) {
		return confirmationTokenRepository.findByConfirmationToken(confirmationToken);
	}

	public void updateToken(ConfirmationToken token) {
		confirmationTokenRepository.save(token);
	}
}
