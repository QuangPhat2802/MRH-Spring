package com.training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.training.dao.UserDao;
import com.training.entity.UserEntity;
import com.training.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{

	@Autowired
	UserDao userDao;
	@Override
	public UserEntity login(String username, String password) {
		UserEntity userEntity = userDao.getUserByUserName(username);
		if(userEntity == null) {
			throw new UsernameNotFoundException("error");
		}
		return userEntity;
	}

}
