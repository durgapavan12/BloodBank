package com.bloodbankapp.security.methods;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.bloodbankapp.security.vo.JwtAuthenticationToken;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

	protected JwtAuthenticationTokenFilter(RequestMatcher requiresAuthenticationRequestMatcher) {

		super(requiresAuthenticationRequestMatcher);
		System.out.println("JwtAuthenticationTokenFilter class JwtAuthenticationTokenFilter method ");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
		System.out.println("JwtAuthenticationTokenFilter class attemptAuthentication method ");
		String authenticationToken = httpServletRequest.getHeader("sessionid");
		JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);
		return getAuthenticationManager().authenticate(token);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		System.out.println("JwtAuthenticationTokenFilter class successfulAuthentication method ");
		chain.doFilter(request, response);
	}
}
