package com.bloodbankapp.security.methods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SecurityUtil {

	public static RequestMatcher getRequestMatcher() {
		
		System.out.println("SecurityUtil  Class And getRequestMatcher method ");
		List<RequestMatcher> requestMatchers = new ArrayList<>();
		for (int i = 0; i < SecurityConstants.AUTHENTICATION_PATHS.length; i++) {
			requestMatchers.add(new AntPathRequestMatcher(SecurityConstants.AUTHENTICATION_PATHS[i]));
		}
		RequestMatcher requestMatcher = new OrRequestMatcher(requestMatchers);
		return requestMatcher;
	}
}
