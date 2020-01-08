package com.bloodbankapp.controllers;

import javax.imageio.spi.RegisterableService;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	@RequestMapping(value="/login",method = RequestMethod.POST,headers = "Accept=application/json")
	public Response login(@RequestBody Login login) {
	Response response=new Response();
	try{
		response=accountService.checkLogin(login);
	} catch (Exception e) {
		response.setStatusCode(ResponseConstants.Error_code);
		response.setStatusMessage("Error in checking blood ");
	}
		return response;
	}
	
	
	@RequestMapping(value="/check",method = RequestMethod.POST,headers = "Accept=application/json")
	public Response bloodCheck(@RequestBody BloodGroup bloodGroup)
	{
		Response response=new Response();
		try {
			if (bloodGroup.getQuantity()<=0)
			{
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("Enter a valid number");
				return response;
			}
		response=accountService.checkBloodDetails(bloodGroup);		
		}catch (Exception e) {
		response.setStatusCode(ResponseConstants.Error_code);
		response.setStatusMessage("Error while checking blood details");			
		}
		return response;		
	}
	
	@RequestMapping(value="/profile",method = RequestMethod.POST)
	public Registration viewProfile(long id) {
	
		Registration profile=new Registration();
		
		try {
			
			profile=accountService.getProfile(id);			
			
		}catch (Exception e) {
		new BloodBankException ("Error while fetching profile");
		}
		
		
		
		return profile;
	}
	
	
	
	
	
	
	
	
}
