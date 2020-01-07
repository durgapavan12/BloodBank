package com.bloodbankapp.daoimplementation;

import org.jongo.Jongo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.bloodbankapp.constants.DB_Constants;
import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.dao.AccountDao;
import com.bloodbankapp.pojos.BloodGroup;
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
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("User already Exist");
		}
		else {
			
			new Jongo(DB_Constants.getMongodbDatabase()).getCollection("registration").insert(registration);
			response.setStatusCode(ResponseConstants.Success_code);
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
			response.setStatusCode(ResponseConstants.Success_code);
			response.setStatusMessage("Successfully logged in!");
		} else {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error while logging ");
		}
		}
		else {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("User Not found");
		}		
		return response;
	}

	@Override
	public Response insertBGData(BloodGroup bloodGroup) {

		Response response=new Response();
		try {
			BloodGroup bg=new Jongo(DB_Constants.getMongodbDatabase()).getCollection("bloodGroups").findOne("{bloodGroup:#}",bloodGroup.getBloodGroup()).as(BloodGroup.class);
			
			if (bg!=null) {			
			int n=bg.getQuantity()+bloodGroup.getQuantity();
			System.out.println(n);
			bg.setQuantity(n);
			System.out.println("amount="+bg.getAmount());
			new Jongo(DB_Constants.getMongodbDatabase()).getCollection("bloodGroups").update("{bloodGroup:#}",bloodGroup.getBloodGroup())
			.upsert().with(bg);
			response.setStatusCode(ResponseConstants.Success_code);
			response.setStatusMessage("Successfully blood group qty inserted");
			}
			else {
			new Jongo(DB_Constants.getMongodbDatabase()).getCollection("bloodGroup").insert(bloodGroup);
			response.setStatusCode(ResponseConstants.Success_code);
			response.setStatusMessage("Successfully inserted new bloodgroup");
			}
		
		}catch (Exception e) {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Error while inserting bloodgroup details");
		}
		
		return response;
	}

}
