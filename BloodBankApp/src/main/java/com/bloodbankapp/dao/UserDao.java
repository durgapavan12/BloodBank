package com.bloodbankapp.dao;

import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;

public interface UserDao {

	Response registration(Registration registration) throws BloodBankException;
	Login loginCheck(Login login) throws BloodBankException;
	Response bloodChecking(BloodGroup bloodGroup) throws BloodBankException;
	Registration viewProfile(long phNo) throws BloodBankException;
	Response profileEdit(Registration registration,long id) throws BloodBankException;
	Response changePassword(String password, String pastPassword, long id) throws BloodBankException;	
}
