package com.bloodbankapp.daoimplementation;

import org.jongo.Jongo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.bloodbankapp.constants.DB_Constants;
import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;

@Repository("accountDao")
public class AccountDaoImplementation implements AccountDao {

	@Override
	public Response registration(Registration registration) {

		Response response = new Response();
		Registration registeredUser = new Jongo(DB_Constants.getMongodbDatabase()).getCollection("registration")
				.findOne("{phNo:#}", registration.getPhNo()).as(Registration.class);

		if (registeredUser != null) {
			response.setStatusCode(600);
			response.setStatusMessage("User already Exist");
		}
		else {
			
			new Jongo(DB_Constants.getMongodbDatabase()).getCollection("registration").insert(registration);
			response.setStatusCode(200);
			response.setStatusMessage("New User Created!");
		}
		return response;
	}

	@Override
	public Response loginCheck(Login login) {
		
		Response response= new Response();
		System.out.println("In Dao impl");
		Registration user=new Jongo(DB_Constants.getMongodbDatabase()).getCollection("registration").findOne("{phNo:#}",login.getPhNo()).as(Registration.class);
		if(user!=null) {
		String generatedlogedPasswordHash = BCrypt.hashpw(login.getPassword(), BCrypt.gensalt(12));			
		boolean b=BCrypt.checkpw(user.getPassword(),generatedlogedPasswordHash );
		if (b) {
			response.setStatusCode(200);
			response.setStatusMessage("Successfully logged in!");
		} else {
			response.setStatusCode(600);
			response.setStatusMessage("Error while logging ");
		}
		}
		else {
			response.setStatusCode(600);
			response.setStatusMessage("User Not found");
		}		
		return response;
	}

}
