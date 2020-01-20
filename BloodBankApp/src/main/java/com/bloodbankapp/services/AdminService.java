package com.bloodbankapp.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Permission;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.pojos.UserPermissions;
import com.bloodbankapp.security.methods.JwtValidator;
import com.bloodbankapp.security.vo.JwtUser;

@Service("adminService")
public class AdminService {

	@Autowired
	AccountDao accountDao;
	
	@Autowired
	private JwtValidator jwtvalidator;
	
	@SuppressWarnings("unused")
	public Response checkAdminLogin(Login login) throws BloodBankException {
		Response resp = new Response();
		Long num=login.getPhNo();
		if (num==null&&login.getPassword()!=null&&login.getPassword()=="") {
			resp.setStatusCode(ResponseConstants.Error_code);
			resp.setStatusMessage("credentials are not in valid format or empty");
			return resp;
		}
			
		Login user = accountDao.checkAdmin(login);
		if (user.getStatusCode()==200) {
			UserPermissions userPermissions = accountDao.getAdminAndUserRoles(1);
			Set<String> permissionSet = new HashSet<String>();
			for (Permission permission : userPermissions.getPermissions()) {
				permissionSet.add(permission.getPermissionName());
			}
			JwtUser jwtUser = new JwtUser();
			jwtUser.setUserPhno(user.getPhNo());
			jwtUser.setUserId(user.getUserId());
			jwtUser.setUserType(JwtUser.ADMIN);
			jwtUser.setRoles(1 + "");

			String sessionToken = jwtvalidator.generate(jwtUser);
			System.out.println("JWt Token :: " + sessionToken);
			user.setSessionToken(sessionToken);
			user.setStatusCode(ResponseConstants.Success_code);
			user.setStatusMessage("successfully login");
			return user;
		} else {
			resp.setStatusCode(ResponseConstants.Error_code);
			resp.setStatusMessage("login failed");
			return resp;
		}

	}
	
	
	public Response insertBloodGroupData(BloodGroup bloodGroup) {
		return accountDao.insertBGData(bloodGroup);
	}
	
	
	public Response updateQuantity(Transaction transaction) {
		return accountDao.updateQuantity(transaction);
	}
	
	public Response removeUser(long phNo) {
		return accountDao.deleteUser(phNo);
	}

	
}
