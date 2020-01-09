package com.bloodbankapp.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
	AccountServices accountService;

	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response registrationNewUser(@RequestBody Registration registration) {
		Response response = new Response();
		try {
			System.out.println("In register");
			response = accountService.registrationUser(registration);
			System.out.println("end register");
		} catch (Exception e) {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error in registration");
		}
		return response;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response login(@RequestBody Login login) {
		Response response = new Response();
		try {
			response = accountService.checkLogin(login);
		} catch (Exception e) {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error in checking blood ");
		}
		return response;
	}

	@RequestMapping(value = "/check", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response bloodCheck(@RequestBody BloodGroup bloodGroup) {
		Response response = new Response();
		try {
			if (bloodGroup.getQuantity() <= 0) {
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("Enter a valid number");
				return response;
			}
			response = accountService.checkBloodDetails(bloodGroup);
		} catch (Exception e) {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error while checking blood details");
		}
		return response;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public Registration viewProfile(long phNo) {
		Registration profile = new Registration();
		try {
			profile = accountService.getProfile(phNo);
		} catch (Exception e) {
			new BloodBankException("Error while fetching profile");
		}
		return profile;
	}

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ArrayList<Transaction> viewTransactions(long phNo) {
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		try {
			list = accountService.getTransactions(phNo);
		} catch (Exception e) {
			new BloodBankException("Error while fetching user transactions");
		}
		return list;
	}

	@RequestMapping(value="/change",method = RequestMethod.POST)
	public Response changePassword(String password,String oldPassword,long id) {
	Response response=new Response();
	try {
	response=accountService.changePassword(password,oldPassword,id);
	}catch (Exception e) {
	response.setStatusCode(ResponseConstants.Error_code);
	response.setStatusMessage("error while changing password");
	}
	return response;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response editProfile(@RequestBody Registration updation, long id) {
		Response response = new Response();
		try {
			response = accountService.editProfile(updation, id);

		} catch (Exception e) {
			new BloodBankException("Error while editing profile");
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Failed to edit");
		}
		return response;

	}

}
