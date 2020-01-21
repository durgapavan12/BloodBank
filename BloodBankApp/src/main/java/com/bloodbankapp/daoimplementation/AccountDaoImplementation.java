package com.bloodbankapp.daoimplementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.bloodbankapp.constants.DB_Constants;
import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Role;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.pojos.UserPermissions;

@Repository("accountDao")
public class AccountDaoImplementation implements AccountDao {
	
	//-----------------to get all the transactions-------------------
	@Override
	public List<Transaction> fetchTransaction() throws BloodBankException {
		try {

		List<Transaction> list = new ArrayList<>();

		Iterator iterator = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getTransactionCol())
				.find().as(Transaction.class).iterator();
		while (iterator.hasNext()) {
			list.add((Transaction) iterator.next());
		}
		return list;
	}catch (Exception e) {
		throw new BloodBankException("Exception Occurred while fetching transactions");
	}
	}
	
	
	//---------------for admin to see all the blood groups & quantity in bank-----------
	@Override
	public ArrayList<BloodGroup> bloodAvailableDetails() throws BloodBankException{
		try {
		ArrayList<BloodGroup> list = new ArrayList<BloodGroup>();

		Iterator iterator = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getBloodgroupCol())
				.find().as(BloodGroup.class).iterator();

		while (iterator.hasNext()) {
			list.add((BloodGroup) iterator.next());
		}
		return list;
	}catch (Exception e) {
		throw new BloodBankException("Exception Occurred while fetching bloodbank details");
			
		}
	}
	
	//-------------------for user to get his transactions--------------
	@Override
	public ArrayList<Transaction> transactionList(long phNo) throws BloodBankException{
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		if(phNo!=0) {
		try {

		Iterator iterator = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getTransactionCol())
				.find("{phNo:#}", phNo).as(Transaction.class).iterator();

		while (iterator.hasNext()) {
			list.add((Transaction) iterator.next());
		}
	
		}catch (Exception e) {
			throw new BloodBankException("Exception Occurred while fetching transactions");
		}
		}else {
			
			return null;
		}
		return list;
	}

	@Override
	public List<Role> getAllRoles() throws BloodBankException {
		Iterator<Role> itr = null;
		List<Role> listOfRoles = new ArrayList<Role>();
		try {
			itr = new Jongo(DB_Constants.getMongodbDatabase()).getCollection("roles").aggregate("{$match:{}}")
					.as(Role.class) // "rolesDetails"
					.iterator();
			while (itr.hasNext()) {
				Role role = new Role();
				role = itr.next();
				listOfRoles.add(role);
			}
		} catch (Exception e) {
			throw new BloodBankException("Exception Occure Wille Feacthing The All Roles ", e);
		}
		return listOfRoles;
	}

	@Override
	public UserPermissions getAdminAndUserRoles(int i) throws BloodBankException {

		Iterator<UserPermissions> itr = null;
		UserPermissions userPermissions = new UserPermissions();
		try {
			itr = new Jongo(DB_Constants.getMongodbDatabase()).getCollection("roles")
					.aggregate("{$match:{roleId:#}}", i)
					.and("{$group:{_id:null,permissionList: { $first: '$permissions'},roleIdList: { $push: '$roleId' }}}")
					.as(UserPermissions.class).iterator();
			if (itr.hasNext()) {
				userPermissions = itr.next();
			}
		} catch (Exception e) {
			throw new BloodBankException("Exception Occure Wille Fetching The All Roles", e);
		}
		return userPermissions;
	}

}
