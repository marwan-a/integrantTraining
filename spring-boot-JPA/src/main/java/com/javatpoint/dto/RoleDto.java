package com.javatpoint.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class RoleDto {
	private String name;
	private ArrayList<PrivilegeDto> privileges;
}
