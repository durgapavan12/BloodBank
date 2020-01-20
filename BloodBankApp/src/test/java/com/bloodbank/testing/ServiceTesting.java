package com.bloodbank.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bloodbankapp.BloodBankAppApplication;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BloodBankAppApplication.class )
public class ServiceTesting {

	@Autowired
	UserService accountServices;

	//private static final Logger logger = Logger.getLogger(ServiceTesting.class);

//	@Test
//	public void insert() {
//		BloodGroup bg=new BloodGroup();
//		bg.setBloodGroup("sania+");
//		bg.setQuantity(12);
//		bg.setAmount(5);
//		Response res=accountServices.insertBloodGroupData(bg);
//		assertEquals(200, res.getStatusCode());
//		//logger.info("Junit working!");
//	}
//	
//	@Test
//	public void regis() {
//		Registration reg=new Registration();
//		reg.setAge(100);
//		reg.setBloodGroup("QWERTY+");
//		reg.setGender("Female");
//		reg.setName("Sheela");
//		reg.setPhNo(12340);
//		reg.setPassword("asdf");
//		accountServices.registrationUser(reg);
//		//assertEquals(200, r.getStatusCode());
//		
//	}

}
