package com.bloodbankapp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.dao.UserDao;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Permission;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Role;
import com.bloodbankapp.pojos.UserPermissions;
import com.bloodbankapp.security.methods.JwtValidator;
import com.bloodbankapp.security.vo.JwtUser;

@Service("userService")
public class UserService {

	@Autowired
	UserDao	userDao;

	@Autowired
	AccountDao accountDao;
	
	@Autowired
	private JwtValidator jwtvalidator;

	public Response registrationUser(Registration registration) throws BloodBankException{
		return userDao.registration(registration);
	}

	public Response checkLogin(Login login) throws BloodBankException{

		Response resp = new Response();
		Login user = userDao.loginCheck(login);
		if (user.getStatusCode()==200) {
			UserPermissions userPermissions = accountDao.getAdminAndUserRoles(2);
			Set<String> permissionSet = new HashSet<String>();
			for (Permission permission : userPermissions.getPermissions()) {
				permissionSet.add(permission.getPermissionName());
			}
			JwtUser jwtUser = new JwtUser();
			jwtUser.setUserPhno(user.getPhNo());
			jwtUser.setUserId(user.getUserId());
			jwtUser.setUserType(JwtUser.USER);
			jwtUser.setRoles(2 + "");

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
	
	public Response checkBloodDetails(BloodGroup bloodGroup) throws BloodBankException{
		return userDao.bloodChecking(bloodGroup);
	}
	
	public Registration getProfile(long phNo) throws BloodBankException{
		return userDao.viewProfile(phNo);
	}

	public Response editProfile(Registration updation, long id) throws BloodBankException{
		return userDao.profileEdit(updation, id);
	}

	public Response changePassword(String newPassword, String oldPassword, long phNo) throws BloodBankException{
		return userDao.changePassword(newPassword, oldPassword, phNo);
	}

	public List<Role> getAllRoles() throws BloodBankException {
		List<Role> listRoles = null;
		listRoles = accountDao.getAllRoles();
		return listRoles;
	}

}
