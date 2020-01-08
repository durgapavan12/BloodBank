package com.bloodbankapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;
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
	
	
	@RequestMapping(value = "/fetch",method = RequestMethod.POST,headers = "Accept=application/json")
	public ArrayList<BloodGroup> fetchAvailbleBloodDetails()
	{
		ArrayList<BloodGroup> list= new ArrayList<BloodGroup>();
		list=accountService.fetchBloodDetails();		
		return list;
	}
	
	
	@RequestMapping(value = "/transfer",method = RequestMethod.POST,headers = "Accept=application/json")
	public Response insertTransaction(@RequestBody Transaction transaction)
	{
		Response response=new Response();
		try {
			response=accountService.insertTransaction(transaction);
		}catch (Exception e) {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error in inserting amount");
		}
		return response;		
	}
	
	@RequestMapping(value = "/view",method = RequestMethod.POST)
	public List<Transaction> fetchTransaction()
	{
		List<Transaction> list=null;
		list=accountService.fetchTransaction();
		
		return list;		
	}
	
	
	@RequestMapping(value="/delete")
	public Response deleteUser(long phNo)
	{
		Response response= new Response();
		try {
			response=accountService.removeUser(phNo);
			}catch (Exception e) {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error in deleting user");
		}
		return response;	
		
	}
	
}