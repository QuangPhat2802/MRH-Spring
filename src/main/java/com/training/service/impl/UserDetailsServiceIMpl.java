//package com.training.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import com.training.dao.UserDao;
//import com.training.entity.UserEntity;
//import com.training.userdetails.MyUserDetails;
//
//public class UserDetailsServiceIMpl implements UserDetailsService{
//
//	@Autowired
//	private UserDao userDao;
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		UserEntity userEntity = userDao.getUserByUserName(username);
//		if(userEntity == null) {
//			 throw new UsernameNotFoundException("Could not find user");
//		}
//		return new MyUserDetails(userEntity);
//	}
//
//}
