package com.javatpoint.services;

import com.javatpoint.dto.UserDto;
import com.javatpoint.exceptions.EmailExistsException;
import com.javatpoint.models.UserRecord;
import com.javatpoint.verification.ConfirmationToken;

public interface IUserService {
	UserRecord registerNewUserAccount(UserDto accountDto) 
		      throws EmailExistsException;
		 
	UserRecord getUser(String verificationToken);
		 
		    void saveRegisteredUser(UserRecord user);
		 
		    void createVerificationToken(UserRecord user, String token);
		 
		    ConfirmationToken getVerificationToken(String VerificationToken);
}