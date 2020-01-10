package com.bloodbankapp.dao;

import java.util.List;

import java.util.ArrayList;

import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Role;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.pojos.UserPermissions;

public interface AccountDao {

	Response registration(Registration registration);
	Registration loginCheck(Login login);
	Response insertBGData(BloodGroup bloodGroup);
	Response insertTransaction(Transaction transaction);
	List<Transaction> fetchTransaction();
	Response bloodChecking(BloodGroup bloodGroup);
	ArrayList<BloodGroup> bloodAvailableDetails();
	Registration viewProfile(long phNo);
	Response deleteUser(long phNo);
	ArrayList<Transaction> transactionList(long phNo);
	Response updateQuantity(Transaction transaction);
	Response profileEdit(Registration registration,long id);
	Response changePassword(String password, String pastPassword, long id);
	List<Role> getAllRoles() throws BloodBankException;
	UserPermissions getAdminAndUserRoles(int i) throws BloodBankException;
	Login checkAdmin(Login login);
}
