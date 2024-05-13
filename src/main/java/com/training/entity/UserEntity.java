package com.training.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class UserEntity {
	
	@Id
	@Column(name = "USERNAME",nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String username;
	
	@Column(name = "PASSWORD",nullable = true)
	private String password;
	
	@Column(name = "ROLE" , nullable = true)
	private String role;
	
	public UserEntity() {
		// TODO Auto-generated constructor stub
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password.toString();
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
