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
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;
import com.bloodbankapp.services.AccountService;
import com.bloodbankapp.services.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BloodBankAppApplication.class)
public class AdminTesting {

	@Autowired
	AdminService adminService;

	@Autowired
	AccountService accountService;
	private static final Logger logger = Logger.getLogger(AdminTesting.class);

	@Test
	public void testadmin() {
		Login login = new Login();
		login.setPhNo(1234567890l);
		login.setPassword("admin");

		try {
			Response res = adminService.checkAdminLogin(login);
			assertEquals(200, res.getStatusCode());
		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testInsertion() {
		BloodGroup bg = new BloodGroup();
		bg.setBloodGroup("O+");
		bg.setQuantity(30);
		bg.setAmount(15);

		Response res;
		try {
			res = adminService.insertBloodGroupData(bg);
			assertEquals(200, res.getStatusCode());
		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testUpdate() {
		Transaction tran = new Transaction();
		tran.setBloodGroup("B+");
		tran.setName("one");
		tran.setPhNo(7845123245l);
		tran.setQuantity(51);
		tran.setStatus("donated");
		tran.setAmount(75);
		try {
			Response res = adminService.updateQuantity(tran);
			assertEquals(200, res.getStatusCode());
		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
		}

	}

	@Test
	public void testfetchAvailbleBloodDetails() {
		ArrayList<BloodGroup> list = new ArrayList<BloodGroup>();
		try {

			list = accountService.fetchBloodDetails();
			/* assertFalse(list.isEmpty()); */
			assertEquals(false, list.isEmpty());
			for (BloodGroup b : list) {
				System.out.println(b.getAmount() + " " + b.getBloodGroup() + " " + b.getQuantity());

			}
		} catch (Exception e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testdelete() {
		try {
			Response res = adminService.removeUser(1231231231l);
			assertEquals(500, res.getStatusCode());
		} catch (Exception e) {
			logger.error("Exception Occured while testing");
		}
	}

	@Test
	public void testViewTransaction() {

		ArrayList<Transaction> list = new ArrayList<Transaction>();
		try {
			list = accountService.getTransactions(7845123245l);
			assertEquals(false, list.isEmpty());
		} catch (BloodBankException e) {
			logger.error("Exception Occured while testing");
		}
	}
}