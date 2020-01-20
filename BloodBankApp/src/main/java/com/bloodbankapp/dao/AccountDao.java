package com.bloodbankapp.dao;

import java.util.ArrayList;
import java.util.List;

import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Role;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.pojos.UserPermissions;

public interface AccountDao {

	List<Transaction> fetchTransaction() throws BloodBankException;
	ArrayList<BloodGroup> bloodAvailableDetails() throws BloodBankException;
	ArrayList<Transaction> transactionList(long phNo) throws BloodBankException;
	List<Role> getAllRoles() throws BloodBankException;
	UserPermissions getAdminAndUserRoles(int i) throws BloodBankException;
	
}
