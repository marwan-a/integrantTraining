package com.javatpoint.dto;

import java.util.ArrayList;


import lombok.Data;

@Data
public class UserFront {
	private long user_id;
	private String email;
	private String name;
	private ArrayList<String> roles;
}
