package com.bloodbankapp.security.methods;

import java.util.HashMap;
import java.util.Map;

public class SecurityConstants {

	public static final String[] IGNORE_RESOURCE_PATHS = {};

	public static final String[] IGNORE_AUTHENTICATION_PATHS = { "/account/**", "/admin/**" };

	public static final String[] AUTHENTICATION_PATHS = { "/admin/secure/**", "/account/secure/**" };

	public static final Map<Long, String> ROLE_MAPPER = new HashMap<>();

	public static final String INTERNAL_ROLE = "INTERNAL";

	public static final String CONSUMER_ROLE = "2";

	public static final String INTERNAL_USER_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJJTlRFUk5BTCIsInVzZXJJZCI6IjAiLCJyb2xlcyI6IklOVEVSTkFMIiwiZXhwaXJ5IjoiMTU3NjIzMDQ5MDAwNyJ9.rnvGQ6s5c2-yyBKfylokSfuebUsIhpEUvsZ4tDB4nHhGmegN3nBdCZVhHbpywnm6ax8oTu5xOYsrJV9kar0wWw";

	private SecurityConstants() {

	}
}
