package com.bloodbankapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.services.AccountServices;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	 AccountServices accountService;	
	
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response login(@RequestBody Login login) throws BloodBankException {
		Response response = new Login();
		try {
			response = accountService.checkAdminLogin(login);
		} catch (Exception e) {
			throw new BloodBankException("Exception occured in checking blood ");
		}
		return response;
	}
	
	@RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=application/json")
	public Response insertBloodData(@RequestBody BloodGroup bloodGroup) throws BloodBankException
	{
		Response response=new Response();
		try {
			response=accountService.insertBloodGroupData(bloodGroup);
		}catch (Exception e) {
			throw new BloodBankException("Exception occured while blood group insertion failed!");
		}
		return response;		
	}
	
	
	@RequestMapping(value = "/fetch",method = RequestMethod.POST,headers = "Accept=application/json")
	public ArrayList<BloodGroup> fetchAvailbleBloodDetails() throws BloodBankException
	{
		try {
		ArrayList<BloodGroup> list= new ArrayList<BloodGroup>();
		list=accountService.fetchBloodDetails();		
		return list;
	}catch (Exception e) {
		throw new BloodBankException("Exception occured while fetching blood groups details failed!");
	}

	}
	
	
//	@RequestMapping(value = "/transfer",method = RequestMethod.POST,headers = "Accept=application/json")
//	public Response insertTransaction(@RequestBody Transaction transaction) throws BloodBankException
//	{
//		Response response=new Response();
//		try {
//			response=accountService.insertTransaction(transaction);
//		}catch (Exception e) {
//			response.setStatusCode(ResponseConstants.Error_code);
//			response.setStatusMessage("Error in inserting amount");
//		}
//		return response;		
//	}
	
	@RequestMapping(value = "/view",method = RequestMethod.POST)
	public List<Transaction> fetchTransaction() throws BloodBankException
	{	try {
		List<Transaction> list=null;
		list=accountService.fetchTransaction();
		return list;
	}
	catch (Exception e) {
		throw new BloodBankException("Exception occured while fetching all transactions");
		
	}
		
	}
	
	
	@RequestMapping(value="/delete")
	public Response deleteUser(long phNo) throws BloodBankException
	{
		Response response= new Response();
		try {
			response=accountService.removeUser(phNo);
			}catch (Exception e) {
				throw new BloodBankException("Exception occured while deleting user");
		}
		return response;	
		
	}
	
	@RequestMapping(value = "/update",method = RequestMethod.POST ,headers = "Accept=application/json")
	public Response updateQuantity(@RequestBody Transaction transaction) throws BloodBankException
	{
		Response response= new Response();
		try {
			response=accountService.updateQuantity(transaction);
			
		}catch (Exception e) {
			throw new BloodBankException("Exception occured while updating of blood quantity and transaction");
		}
		
		return response;		
	}
		
	}