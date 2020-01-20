package com.bloodbankapp.dao;

import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;

public interface AdminDao {

	Login checkAdmin(Login login) throws BloodBankException;
	Response insertBGData(BloodGroup bloodGroup) throws BloodBankException;
	Response updateQuantity(Transaction transaction) throws BloodBankException;
	Response insertTransaction(Transaction transaction) throws BloodBankException;
	Response deleteUser(long phNo) throws BloodBankException;
}
