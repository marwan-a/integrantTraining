package com.javatpoint.dto;

import javax.validation.constraints.NotBlank;

import com.javatpoint.validations.FirstOrder;
import com.javatpoint.validations.PasswordsEqualConstraint;
import com.javatpoint.validations.SecondOrder;
import com.javatpoint.validations.ValidEmail;
import com.javatpoint.validations.ValidPassword;

import lombok.Data;


@Data
@PasswordsEqualConstraint(message = "passwords are not equal")
public class UserDto {
    private @NotBlank(message = "Name is mandatory",groups = FirstOrder.class) String name;
     
    @NotBlank(groups = FirstOrder.class)
    @ValidPassword(groups = SecondOrder.class)
    private String password;
    @NotBlank(groups = FirstOrder.class)
    private String matchingPassword;
     
    private @NotBlank(message = "Email is mandatory",groups = FirstOrder.class) @ValidEmail(groups = SecondOrder.class) String email;

}