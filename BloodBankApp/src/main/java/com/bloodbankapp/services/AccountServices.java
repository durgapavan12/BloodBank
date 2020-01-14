package com.bloodbankapp.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Permission;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Role;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.pojos.UserPermissions;
import com.bloodbankapp.security.methods.JwtValidator;
import com.bloodbankapp.security.vo.JwtUser;

@Service("accountService")
public class AccountServices {

	@Autowired
	AccountDao accountDao;

	@Autowired
	private JwtValidator jwtvalidator;

	public Response registrationUser(Registration registration) {
		return accountDao.registration(registration);
	}

	public Response checkLogin(Login login) throws BloodBankException {

		Response resp = new Response();
		Login user = accountDao.loginCheck(login);
		if (user != null) {
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

	public Response insertBloodGroupData(BloodGroup bloodGroup) {
		return accountDao.insertBGData(bloodGroup);
	}

//	public Response insertTransaction(Transaction transaction) {
//		return accountDao.insertTransaction(transaction);
//	}

	public List<Transaction> fetchTransaction() {
		return accountDao.fetchTransaction();
	}

	public Response checkBloodDetails(BloodGroup bloodGroup) {
		return accountDao.bloodChecking(bloodGroup);
	}

	public ArrayList<BloodGroup> fetchBloodDetails() {
		return accountDao.bloodAvailableDetails();
	}

	public Registration getProfile(long phNo) {
		return accountDao.viewProfile(phNo);
	}

	public Response removeUser(long phNo) {
		return accountDao.deleteUser(phNo);
	}

	public ArrayList<Transaction> getTransactions(long phNo) {
		return accountDao.transactionList(phNo);
	}

	public Response updateQuantity(Transaction transaction) {
		return accountDao.updateQuantity(transaction);
	}

	public Response editProfile(Registration updation, long id) {
		return accountDao.profileEdit(updation, id);
	}

	public Response changePassword(String password, String pastPassword, long id) {
		return accountDao.changePassword(password, pastPassword, id);
	}

	public List<Role> getAllRoles() throws BloodBankException {
		List<Role> listRoles = null;
		listRoles = accountDao.getAllRoles();
		return listRoles;
	}

	public Response checkAdminLogin(Login login) throws BloodBankException {
		Response resp = new Response();
		Login user = accountDao.checkAdmin(login);
		if (user != null) {
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
}
