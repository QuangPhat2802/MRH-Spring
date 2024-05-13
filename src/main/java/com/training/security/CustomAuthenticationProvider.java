package com.training.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.training.entity.UserEntity;
import com.training.service.IUserService;
import com.training.test.PasswordGenerator;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
	@Autowired
	IUserService userService;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		String passwordMd5 = PasswordGenerator.ecryptMD5(password);
		UsernamePasswordAuthenticationToken usernamePassAuthToken = null;

		UserEntity loginUser = userService.login(username, password);
		
		if (loginUser.getUsername().equals(username) && loginUser.getPassword().equals(password)) {
			List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			usernamePassAuthToken = new UsernamePasswordAuthenticationToken(username, StringUtils.EMPTY, grantedAuths);

		}
		return usernamePassAuthToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
