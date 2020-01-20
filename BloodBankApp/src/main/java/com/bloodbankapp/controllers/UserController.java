package com.bloodbankapp.controllers;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.services.AccountService;
import com.bloodbankapp.services.UserService;

@RestController
@RequestMapping("/account")
public class UserController {
	
	private static final Logger logger  = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response registrationNewUser(@RequestBody Registration registration) {
		Response response = new Response();
		try {
			response = userService.registrationUser(registration);
		} catch (Exception e) {
			logger.error("Exception occured while Registration", e);
		}
		return response;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response login(@RequestBody Login login) {
		Response response = new Login();
		try {
			response = userService.checkLogin(login);
		} catch (Exception e) {
			logger.error("Exception occured in checking blood ",e);
		}
		return response;
	}

	@RequestMapping(value = "/check", method = RequestMethod.GET, headers = "Accept=application/json")
	public Response bloodCheck(@RequestBody BloodGroup bloodGroup) {
		Response response = new Response();
		try {
			if (bloodGroup.getQuantity() <= 0) {
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("Enter a valid number");
				return response;
			}
			response = userService.checkBloodDetails(bloodGroup);
		} catch (Exception e) {
			
			logger.error("Error in blood checking detail",e);
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error while checking blood details");
		}
		return response;
	}


	@PreAuthorize("hasPermission('null','viewUser')")
	@RequestMapping(value = "/secure/profile", method = RequestMethod.GET)
	public Registration viewProfile(long phNo) {
		Registration profile =null;
		try {
			profile = userService.getProfile(phNo);
		} catch (Exception e) {
			//new BloodBankException("Exception occurred while fetching profile");
			logger.error("Exception occured while fetching profile",e);
		}
		return profile;
	}

	
	@PreAuthorize("hasPermission('null','viewUserTransaction')")
	@RequestMapping(value = "/secure/transactions", method = RequestMethod.GET)
	public ArrayList<Transaction> viewTransactions(long phNo) {
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		try {
			list = accountService.getTransactions(phNo);
		} catch (Exception e) {
			//throw new BloodBankException("Exception occurred while fetching user transactions");
			
			logger.error("Exception occurred while fetching user transactions",e);
		}
		return list;
	}

	@PreAuthorize("hasPermission('null','updatePassword')")
	@RequestMapping(value="/secure/change",method = RequestMethod.POST)
	public Response changePassword(String newPassword,String oldPassword,long phNo) throws BloodBankException {
	Response response=new Response();
	try {
	response=userService.changePassword(newPassword,oldPassword,phNo);
	}catch (Exception e) {
		//throw new BloodBankException("Exception occurred while changing password");
		logger.error("Exception occurred while changing password",e);
	}
	return response;
	}

	@PreAuthorize("hasPermission('null','updateProfile')")
	@RequestMapping(value = "/secure/edit", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response editProfile(@RequestBody Registration updation, long id) {
		Response response = new Response();
		try {
			response = userService.editProfile(updation, id);

		} catch (Exception e) {
			//new BloodBankException("Exception occurred while editing profile");
			logger.error("Exception occurred while editing profile",e);
		}
		return response;
	}

}
