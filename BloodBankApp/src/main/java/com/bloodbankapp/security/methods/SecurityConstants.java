package com.bloodbankapp.security.methods;

import java.util.HashMap;
import java.util.Map;

public class SecurityConstants {

	public static final String[] IGNORE_RESOURCE_PATHS = {};

	public static final String[] IGNORE_AUTHENTICATION_PATHS = { "/account/**", "/userprofile/**", "/healthsocial/**",
			"/phr/**", "/dashboard/**", "/phr/report/**", "/hspackage/**", "/advice/**", "/immunizationadvice/**",
			"/symptomadvice/**", "/dietadvice/**", "/physicaladvice/**", "/fitnessdevice/**", "/mCommerce/**",
			"/calendar/**", "/fthvital/**", "/medicaladvice/**", "/phr/vital/**", "/notification/*", "/dialogflow/**",
			"/doctor/report/**", "/diabetic/**", "/healthtips/**", "/campaign/**", "/migration/**", "/quitalcohol/**",
			"/account/onboarding/**" };

	public static final String[] AUTHENTICATION_PATHS = { "/account/secure/*","/admin/*"};

	public static final Map<Long, String> ROLE_MAPPER = new HashMap<>();

	public static final String INTERNAL_ROLE = "INTERNAL";

	public static final String CONSUMER_ROLE = "2";

	public static final String INTERNAL_USER_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJJTlRFUk5BTCIsInVzZXJJZCI6IjAiLCJyb2xlcyI6IklOVEVSTkFMIiwiZXhwaXJ5IjoiMTU3NjIzMDQ5MDAwNyJ9.rnvGQ6s5c2-yyBKfylokSfuebUsIhpEUvsZ4tDB4nHhGmegN3nBdCZVhHbpywnm6ax8oTu5xOYsrJV9kar0wWw";

	private SecurityConstants() {

	}
}
