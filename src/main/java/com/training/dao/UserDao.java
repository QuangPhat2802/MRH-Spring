package com.training.dao;

import com.training.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserEntity, String>  {

	@Query("SELECT u FROM UserEntity u WHERE u.username = :username")
	UserEntity getUserByUserName(@Param("username") String username);
	
}
