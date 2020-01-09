package com.bloodbankapp.services;

import java.util.ArrayList;
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
		System.out.println("in service");
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
	

	public Response checkBloodDetails(BloodGroup bloodGroup) {
		return accountDao.bloodChecking(bloodGroup);
	}

	public ArrayList<BloodGroup> fetchBloodDetails() {
		return accountDao.bloodAvailableDetails();
	}

	public Registration getProfile(long phNo){
		return accountDao.viewProfile(phNo);
	}

	public Response removeUser(long phNo) {
		return accountDao.deleteUser(phNo);
	}

	public ArrayList<Transaction> getTransactions(long phNo) {
		return accountDao.transactionList(phNo);
	}

	public Response updateQuantity(Transaction transaction) {
		return accountDao.updateQuantity(transaction);
	}

	public Response editProfile(Registration updation,long id) {
		return accountDao.profileEdit(updation,id);
	}
	
	public Response changePassword(String password, String pastPassword,long id) {
		return accountDao.changePassword(password,pastPassword,id);
		}

	
	
}
