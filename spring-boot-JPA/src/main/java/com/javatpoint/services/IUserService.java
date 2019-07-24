package com.javatpoint.services;

import com.javatpoint.dto.UserDto;
import com.javatpoint.exceptions.EmailExistsException;
import com.javatpoint.models.UserRecord;

public interface IUserService {
	UserRecord registerNewUserAccount(UserDto accountDto) 
		      throws EmailExistsException;
}