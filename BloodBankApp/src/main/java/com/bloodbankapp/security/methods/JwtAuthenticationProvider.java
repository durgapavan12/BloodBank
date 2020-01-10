package com.bloodbankapp.security.methods;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bloodbankapp.security.vo.JwtAuthenticationToken;
import com.bloodbankapp.security.vo.JwtUser;
import com.bloodbankapp.security.vo.JwtUserDetails;

import io.jsonwebtoken.JwtException;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private JwtValidator validator;

	@Autowired
	private HttpServletRequest request;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		System.out.println("JwtAuthenticationProvider class additionalAuthenticationChecks method ");

	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
		System.out.println("JwtAuthenticationProvider class retrieveUser method ");
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
		String token = jwtAuthenticationToken.getToken();
		JwtUser jwtUser = validator.validate(token);

		if (jwtUser == null) {
			throw new JwtException("JWT Token is incorrect");
		}
		if ((jwtUser.getUserType() == JwtUser.ADMIN || jwtUser.getUserType() == JwtUser.USER)
				&& jwtUser.getExpiry() < System.currentTimeMillis()) {
			throw new JwtException("JWT Token has expired");
		}

		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList(jwtUser.getRoles());

		request.setAttribute("userId", jwtUser.getUserId());
		request.setAttribute("userType", jwtUser.getUserType());

		return new JwtUserDetails(String.valueOf(jwtUser.getUserPhno()), jwtUser.getUserId(), token, grantedAuthorities);
	}

	@Override
	public boolean supports(Class<?> aClass) {
		System.out.println("JwtAuthenticationProvider class supports method ");
		return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
	}
}
