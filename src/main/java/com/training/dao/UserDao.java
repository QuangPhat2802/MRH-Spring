package com.training.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.training.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, String>  {

	@Query("SELECT u FROM UserEntity u WHERE u.username = :username")
	UserEntity getUserByUserName(@Param("username") String username);
	
}
