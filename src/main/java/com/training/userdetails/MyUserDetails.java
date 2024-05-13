//package com.training.userdetails;
//
//import java.util.Arrays;
//import java.util.Collection;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import com.training.entity.UserEntity;
//
//public class MyUserDetails implements UserDetails {
//
//	private UserEntity userEntity;
//	
//	public MyUserDetails(UserEntity userEntity) {
//		this.userEntity = userEntity;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userEntity.getRole());
//		
//		return Arrays.asList(authority);
//	}
//
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return userEntity.getPassword();
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return userEntity.getUsername();
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//}
