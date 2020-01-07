package com.bloodbankapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.services.AccountServices;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AccountServices accountService;	
	
	@RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=application/json")
	public Response insertBloodData(@RequestBody BloodGroup bloodGroup)
	{
		Response response=new Response();
		try {
			response=accountService.insertBloodGroupData(bloodGroup);
		}catch (Exception e) {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error in inserting BloodGroup Data");
		}
		return response;		
	}
}
