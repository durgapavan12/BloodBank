package com.bloodbankapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
		System.out.println("Welcome To Bload Bank");
		try {
			response = accountService.registrationUser(registration);
		} catch (Exception e) {
			response.setStatusCode(600);
			response.setStatusMessage("Error in registration");
		}
		return response;
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST,headers = "Accept=application/json")
	public Response login(@RequestBody Login login) {
	Response response=new Response();
	
	System.out.println("In login Controller");
	
	try{
		response=accountService.checkLogin(login);
	}catch (Exception e) {
		response.setStatusCode(600);
		response.setStatusMessage("Error in logging");
	}
		return response;
	}

}
