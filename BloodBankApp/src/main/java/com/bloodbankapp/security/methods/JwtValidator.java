package com.bloodbankapp.security.methods;

import org.springframework.stereotype.Component;

import com.bloodbankapp.security.vo.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtValidator {

	private static final String USER_ID = "userId";
	private static final String ROLES = "roles";
	private static final String EXPIRY = "expiry";

	private String secret = "0D44A78AA5B3E1E67F5C31019D3EB505E7C5ABA757F319C209EF4648D282E5F6EA36742B0C99769DCB27DFA7986D3720C4AD75D2B4827AEB78960C0BDB243EB5";

	public JwtUser validate(String token) {
		JwtUser jwtUser = null;
		try {
			Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			jwtUser = new JwtUser();
			jwtUser.setUserPhno(Long.parseLong(body.getSubject()));
			jwtUser.setUserId(Long.parseLong((String) body.get(USER_ID)));
			jwtUser.setRoles((String) body.get(ROLES));
			jwtUser.setExpiry(Long.parseLong((String) body.get(EXPIRY)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jwtUser;
	}

	public String generate(JwtUser jwtUser) {
		Claims claims = Jwts.claims().setSubject(String.valueOf(jwtUser.getUserPhno()));
		claims.put(USER_ID, String.valueOf(jwtUser.getUserId()));
		claims.put(ROLES, jwtUser.getRoles());
		claims.put(EXPIRY, String.valueOf(System.currentTimeMillis() + (1 * 24 * 60 * 60 * 1000)));
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}