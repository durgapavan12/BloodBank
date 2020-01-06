package com.bloodbankapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;

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
	
}
