package com.bloodbankapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;

@Service("accountService")
public class AccountServices {

	@Autowired
	AccountDao accountDao;

	public Response registrationUser(Registration registration) {
		return accountDao.registration(registration);
	}
	
	public Response checkLogin(Login login) {
		return accountDao.loginCheck(login);
	}

	public Response insertBloodGroupData(BloodGroup bloodGroup) {
		return accountDao.insertBGData(bloodGroup);
	}	
	
	public Response insertTransaction(Transaction transaction) {
		return accountDao.insertTransaction(transaction);
	}	
	
	public List<Transaction> fetchTransaction() {
		return accountDao.fetchTransaction();
	}	
	
}
