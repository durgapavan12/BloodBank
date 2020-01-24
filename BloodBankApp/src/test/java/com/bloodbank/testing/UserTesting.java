package com.bloodbank.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bloodbankapp.BloodBankAppApplication;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.services.AccountService;
import com.bloodbankapp.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BloodBankAppApplication.class)
public class UserTesting {

	private static final Logger logger=Logger.getLogger(UserTesting.class);
	@Autowired
	UserService userService;

	@Autowired
	AccountService accountService;

	@Test
	public void testRegister() {
		Registration reg = new Registration();
		reg.setName("user2");
		reg.setPhNo(96584);
		reg.setPassword("asdf");
		reg.setGender("male");
		reg.setAge(21);
		reg.setBloodGroup("B+");

		try {
			Response res = userService.registrationUser(reg);
			assertEquals(200, res.getStatusCode());
		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testLogin() {
		Login login = new Login();
		login.setPhNo(1212121211l);
		login.setPassword("asdf");
		try {
			Response response = userService.checkLogin(login);
			assertEquals(200, response.getStatusCode(), "login test passed");

		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
			}
	}

	@Test
	public void testCheck() {
		BloodGroup bg = new BloodGroup();
		bg.setBloodGroup("B+");
		bg.setQuantity(510);

		try {
			Response res = userService.checkBloodDetails(bg);
			assertEquals(200, res.getStatusCode());
		} catch (Exception e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testViewprofile() {
		try {

			Registration profile = userService.getProfile(7845123245l);
			assertEquals(200, profile.getStatusCode());
		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testUserTransactions() {
		ArrayList<Transaction> list = new ArrayList<Transaction>();
		try {

			long phNo = 7845123245l;
			list = accountService.getTransactions(phNo);
			/* assertFalse(list.isEmpty()); */
			assertEquals(false, list.isEmpty());
			for (Transaction b : list) {
				System.out.println(b.getName() + " " + b.getStatus() + " " + b.getQuantity());
			}
		} catch (Exception e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testPasswordChange() {
		try {
			String old = "asdf";
			String np = "newpass1";
			Response res = userService.changePassword(np, old, 7845123245l);
			assertEquals(200, res.getStatusCode());
		} catch (Exception e) {
			logger.error("Exception Occured while testing");
			}
	}

	@Test
	public void testEditprofile() {
		int id = 1004;
		Registration reg = new Registration();
		reg.setAge(25);
		reg.setBloodGroup("A+");
		reg.setGender("female");
		reg.setName("durga");
		reg.setPhNo(7845123245l);
		try {
			Response response = userService.editProfile(reg, id);
			assertEquals(200, response.getStatusCode());

		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
			}
	}
}

