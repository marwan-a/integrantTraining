package com.javatpoint.dto;

import java.util.ArrayList;

import lombok.Data;

@Data
public class RoleDto {
	private long role_id;
	private String name;
	private ArrayList<String> privileges;
}
