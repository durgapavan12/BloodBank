
package com.bloodbankapp.security.methods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.bloodbankapp.pojos.Role;
import com.bloodbankapp.security.vo.JwtUserDetails;
import com.bloodbankapp.services.AccountServices;

@Component
public class CustomPermissionHandler implements PermissionEvaluator {
	
	@Autowired
	private AccountServices accountService;
	private static Map<Integer, ArrayList<String>> permissionMap = null;

	@Override
	public boolean hasPermission(Authentication authentication, Object accessType, Object permission) {
		boolean hasAccess = false;
		setPermissionsBaseData();
		Iterator<? extends GrantedAuthority> itr = authentication.getAuthorities().iterator();

		List<Integer> roleIdList = new ArrayList<>();
		while (itr.hasNext()) {
			GrantedAuthority grantedAuthority = (GrantedAuthority) itr.next();
			if (grantedAuthority != null && grantedAuthority.getAuthority() != null) {
				roleIdList.add(Integer.parseInt(grantedAuthority.getAuthority()));
			}
		}

		if (authentication != null && roleIdList != null && !roleIdList.isEmpty()) {
			Set<String> userPermissionSet = new HashSet<>();
			try {
				for (Integer roleId : roleIdList) {
					if (roleId > 0 && permissionMap.containsKey(roleId)) {
						userPermissionSet.addAll(permissionMap.get(roleId));
					} else {
						System.out.println("Could not find the matching permissions for role " + roleId);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			String permissions = permission.toString();
			String[] methodPermissions = permissions.split(",");
			for (String tmpPermission : methodPermissions) {
				hasAccess = validateAccess(tmpPermission.trim(), userPermissionSet);
				if (hasAccess) {
					break;
				}
			}
		}
		return hasAccess;
	}

	private boolean validateAccess(String permission, Set<String> userPermissionSet) {
		boolean permissionCheck = false;
		if (userPermissionSet != null && userPermissionSet.size() > 0 && userPermissionSet.contains(permission)) {
			permissionCheck = true;
		}
		return permissionCheck;
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		boolean hasAccess = false;
		hasAccess = hasPermission(authentication, targetType, permission);
		Iterator<? extends GrantedAuthority> itr = authentication.getAuthorities().iterator();
		if (hasAccess) {
			while (itr.hasNext()) {
				GrantedAuthority grantedAuthority = (GrantedAuthority) itr.next();
				if (grantedAuthority != null && grantedAuthority.getAuthority() != null) {
					if (grantedAuthority.getAuthority().equals(SecurityConstants.INTERNAL_ROLE)) {
						return true;
					}
				}
			}
			JwtUserDetails jwtUserDetails = (JwtUserDetails) authentication.getPrincipal();
//			hasAccess = criticalSecurityHandler.checkForCriticalAccess(targetId, targetType, jwtUserDetails.getId());
		}
		return hasAccess;
	}

	private Map<Integer, ArrayList<String>> setPermissionsBaseData() {
		try {
			if (permissionMap == null || permissionMap.size() == 0) {
				permissionMap = new HashMap<>();
				List<Role> roleList = accountService.getAllRoles();
				if (roleList.size() > 0) {
					for (int i = 0; i < roleList.size(); i++) {
						ArrayList<String> accessList = new ArrayList<>();
						Role role = roleList.get(i);
						for (int j = 0; j < role.getPermission().size(); j++) {
							
								accessList.add(role.getPermission().get(j).getPermisssionName());
					
						}
						permissionMap.put(role.getRoleId(), accessList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permissionMap;
	}

}
