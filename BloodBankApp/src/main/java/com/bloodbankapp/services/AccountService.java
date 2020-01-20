package com.bloodbankapp.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Transaction;

@Service("accountService")
public class AccountService {
	
		@Autowired
		AccountDao accountDao;
		
		public ArrayList<BloodGroup> fetchBloodDetails()throws BloodBankException {
			return accountDao.bloodAvailableDetails();
		}
		
		public List<Transaction> fetchTransaction() throws BloodBankException {
			return accountDao.fetchTransaction();
		}
		
		public ArrayList<Transaction> getTransactions(long phNo) throws BloodBankException{
			return accountDao.transactionList(phNo);
		}

}
