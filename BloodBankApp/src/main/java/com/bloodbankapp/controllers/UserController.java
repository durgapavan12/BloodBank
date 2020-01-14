package com.bloodbankapp.controllers;

import java.util.ArrayList;

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
import com.bloodbankapp.services.AccountServices;

@RestController
@RequestMapping("/account")
public class UserController {

	@Autowired
	private AccountServices accountService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response registrationNewUser(@RequestBody Registration registration) throws BloodBankException {
		Response response = new Response();
		try {
			response = accountService.registrationUser(registration);
		} catch (Exception e) {
			throw new BloodBankException("Exception occured while Registration");
		}
		return response;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response login(@RequestBody Login login) throws BloodBankException {
		Response response = new Login();
		try {
			response = accountService.checkLogin(login);
		} catch (Exception e) {
			throw new BloodBankException("Exception occured in checking blood ");
		}
		return response;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response bloodCheck(@RequestBody BloodGroup bloodGroup) throws BloodBankException {
		Response response = new Response();
		try {
			if (bloodGroup.getQuantity() <= 0) {
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("Enter a valid number");
				return response;
			}
			response = accountService.checkBloodDetails(bloodGroup);
		} catch (Exception e) {
			throw new BloodBankException("Exception occured while checking blood details");
		}
		return response;
	}


	@PreAuthorize("hasPermission('null','viewUser')")
	@RequestMapping(value = "/secure/profile", method = RequestMethod.GET)
	public Registration viewProfile(long phNo) {
		Registration profile =null;
		try {
			profile = accountService.getProfile(phNo);
		} catch (Exception e) {
			new BloodBankException("Exception occured while fetching profile");
		}
		return profile;
	}

	
	@PreAuthorize("hasPermission('null','viewUserTransaction')")
	@RequestMapping(value = "/secure/transactions", method = RequestMethod.GET)
	public ArrayList<Transaction> viewTransactions(long phNo) throws BloodBankException {
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		try {
			list = accountService.getTransactions(phNo);
		} catch (Exception e) {
			throw new BloodBankException("Exception occured while fetching user transactions");
		}
		return list;
	}

	@PreAuthorize("hasPermission('null','updatePassword')")
	@RequestMapping(value="/secure/change",method = RequestMethod.POST)
	public Response changePassword(String password,String oldPassword,long id) throws BloodBankException {
	Response response=new Response();
	try {
	response=accountService.changePassword(password,oldPassword,id);
	}catch (Exception e) {
		throw new BloodBankException("Exception occured while changing password");
	}
	return response;
	}

	@PreAuthorize("hasPermission('null','updateProfile')")
	@RequestMapping(value = "/secure/edit", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response editProfile(@RequestBody Registration updation, long id) {
		Response response = new Response();
		try {
			response = accountService.editProfile(updation, id);

		} catch (Exception e) {
			new BloodBankException("Exception occured while editing profile");
		}
		return response;
	}

}
