package com.bloodbankapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.services.AccountService;
import com.bloodbankapp.services.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;	
	
	
	@Autowired
	AccountService accountService;
	
	
	private static final Logger logger  = Logger.getLogger(AdminController.class);
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	public Response login(@RequestBody Login login) 
	{		
		Response response = new Login();
		try {
			response = adminService.checkAdminLogin(login);
		} catch (Exception e) {
			//throw new BloodBankException("Exception occurred in checking blood ");
			logger.error("Exception occured in checking blood",e);
		}
		return response;
	}
	
	

	@RequestMapping(value = "/insert",method = RequestMethod.POST,headers = "Accept=application/json")
	public Response insertBloodData(@RequestBody BloodGroup bloodGroup) 
	{
		Response response=new Response();
		try {
			response=adminService.insertBloodGroupData(bloodGroup);
		}catch (Exception e) {
			//throw new BloodBankException("Exception occurred while blood group insertion failed!");
			logger.error("Exception occurred while blood group insertion failed!",e);
		}
		return response;		
	}
	
	
	@RequestMapping(value = "/fetch", method = RequestMethod.GET, headers = "Accept=application/json")
	public ArrayList<BloodGroup> fetchAvailbleBloodDetails()
	{
		ArrayList<BloodGroup> list = new ArrayList<BloodGroup>();
		try {

			list = accountService.fetchBloodDetails();

		}

		catch (Exception e) {
			// throw new BloodBankException("Exception occurred while fetching blood groups details failed!");
			logger.error("Exception occurred while fetching blood groups details failed!", e);
		}
		return list;

	}
	
	
	@PreAuthorize("hasPermission('null','viewTransactions')")
	@RequestMapping(value = "/secure/view",method = RequestMethod.GET)
	public List<Transaction> fetchTransaction()
	{
		List<Transaction> list=null;
		try {
		
		list=accountService.fetchTransaction();
		
	}
	catch (Exception e) {
			// throw new BloodBankException("Exception occurred while fetching all transactions");
			
		logger.error("Exception occurred while fetching all transactions", e);
	}
		return list;
	}
	
	@PreAuthorize("hasPermission('null','deleteUser')")
	@DeleteMapping(value="/secure/delete")
	public Response deleteUser(long phNo)
	{
		Response response= new Response();
		try {
			response=adminService.removeUser(phNo);
			}catch (Exception e) {
				//throw new BloodBankException("Exception occurred while deleting user");
				logger.error("Exception occurred while deleting user",e);
		}
		return response;	
		
	}
	
	
	@PreAuthorize("hasPermission('null','updateBlood')")
	@RequestMapping(value = "/secure/update",method = RequestMethod.POST ,headers = "Accept=application/json")
	public Response updateQuantity(@RequestBody Transaction transaction) 
	{
		Response response= new Response();
		try {
			response=adminService.updateQuantity(transaction);
			
		}catch (Exception e) {
			//throw new BloodBankException("Exception occurred while updating of blood quantity and transaction");
			
			logger.error("Exception occurred while updating of blood quantity and transaction",e);
		}
		
		return response;		
	}
		
	}