package com.training.service;

import com.training.entity.UserEntity;

public interface IUserService {
	UserEntity login(String username,String password);
}
