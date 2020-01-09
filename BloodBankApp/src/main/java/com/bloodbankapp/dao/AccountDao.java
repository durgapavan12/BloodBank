package com.bloodbankapp.dao;

import java.util.List;

import java.util.ArrayList;

import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;

public interface AccountDao {

	Response registration(Registration registration);
	Response loginCheck(Login login);
	Response insertBGData(BloodGroup bloodGroup);
	Response insertTransaction(Transaction transaction);
	List<Transaction> fetchTransaction();
	Response bloodChecking(BloodGroup bloodGroup);
	ArrayList<BloodGroup> bloodAvailableDetails();
	Registration viewProfile(long phNo);
	Response deleteUser(long phNo);
	Response changePassword(String password, String pastPassword,long id);
	
	 

}
