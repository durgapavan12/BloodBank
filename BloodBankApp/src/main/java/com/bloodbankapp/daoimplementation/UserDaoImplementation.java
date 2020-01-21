package com.bloodbankapp.daoimplementation;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jongo.Jongo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.bloodbankapp.constants.DB_Constants;
import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.dao.UserDao;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Counter;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;

@Repository("userDao")
public class UserDaoImplementation implements UserDao {

	private static final Logger logger = Logger.getLogger(UserDaoImplementation.class);

	// ------------------For user registration----------------
	@Override
	public Response registration(Registration registration) throws BloodBankException {

		Response response = new Response();
		if ((registration.getName() != null) && (registration.getPassword() != null)
				&& (registration.getBloodGroup() != null) && (registration.getName() != "")
				&& (registration.getBloodGroup() != "") && (registration.getPassword() != "")
				&& (registration.getAge() != 0) && (registration.getPhNo() != 0) && (registration.getGender() != null)
				&& (registration.getGender() != "")) {

			try {
				Registration registeredUser = new Jongo(DB_Constants.getMongodbDatabase())
						.getCollection(DB_Constants.getRegistrationCol()).findOne("{phNo:#}", registration.getPhNo())
						.as(Registration.class);

				if (registeredUser != null) {
					response.setStatusCode(ResponseConstants.Error_code);
					response.setStatusMessage("User already Exist");

				} else {
					new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getCounterCol())
							.update("{ _id:1 }").with("{$inc:{seq:1}}");
					Counter counter = new Jongo(DB_Constants.getMongodbDatabase())
							.getCollection(DB_Constants.getCounterCol()).findOne("{ _id: 1}").as(Counter.class);

					registration.setId(counter.getSeq());
					new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getRegistrationCol())
							.insert(registration);
					response.setStatusCode(ResponseConstants.Success_code);
					response.setStatusMessage("New User Created!");
				}
			} catch (Exception e) {
				throw new BloodBankException("Exception Occured while registration", e);
			}
		} else {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("field are empty");
		}
		return response;
	}

	// -----------------------User Logging Check------------------------
	@Override
	public Login loginCheck(Login login) throws BloodBankException {
		Login user = null;
		Login error=new Login();
		try {
			if (login != null && login.getPhNo() > 0 && login.getPassword() != null && login.getPassword() != "") {

				user = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getRegistrationCol())
						.findOne("{phNo:#}", login.getPhNo()).as(Login.class);
				if (user != null) {
					String generatedlogedPasswordHash = BCrypt.hashpw(login.getPassword(), BCrypt.gensalt(12));
					boolean b = BCrypt.checkpw(user.getPassword(), generatedlogedPasswordHash);
					if (b) {
						user.setStatusCode(ResponseConstants.Success_code);
						user.setStatusMessage("Successfully logged in!");

					} else {
						user.setStatusCode(ResponseConstants.Error_code);
						user.setStatusMessage("Error while logging ");
					}
				} 
				else{
					error.setStatusMessage("User Not found");
					error.setStatusCode(ResponseConstants.Error_code);
					return error;
				}

			} else {
				error.setStatusCode(ResponseConstants.Error_code);
				error.setStatusMessage("Field are invalid form or empty");
				return error;
			}
		} catch (Exception e) {
			user.setStatusCode(ResponseConstants.Error_code);
			user.setStatusMessage("Exception Occured");
			throw new BloodBankException("Exception Occured inside implementation of AdminDao", e);
		}
		return user;

	}

	// -----------------for user blood checking service--------------
	@Override
	public Response bloodChecking(BloodGroup bloodGroup) throws BloodBankException {
		Response response = new Response();
		if ((bloodGroup.getBloodGroup() != null) && (bloodGroup.getBloodGroup() != "")) {

			try {
				BloodGroup bg = new Jongo(DB_Constants.getMongodbDatabase())
						.getCollection(DB_Constants.getBloodgroupCol())
						.findOne("{bloodGroup:#}", bloodGroup.getBloodGroup()).as(BloodGroup.class);
				if (bg != null) {
					if (bg.getQuantity() > bloodGroup.getQuantity()) {
						response.setStatusCode(ResponseConstants.Success_code);
						response.setStatusMessage("Blood Group availble and cost around "
								+ bg.getAmount() * bloodGroup.getQuantity() + "/- for your required quantity");
					} else {
						response.setStatusCode(ResponseConstants.Success_code);
						response.setStatusMessage("Only " + bg.getQuantity() + " units is available and costs around "
								+ bg.getQuantity() * bg.getAmount() + " /-for available quantity ");
					}
				} else {
					response.setStatusCode(ResponseConstants.Error_code);
					response.setStatusMessage("Failed to fetch blood details as blood group is not available");
				}
			} catch (Exception e) {
				throw new BloodBankException("Exception occured while checking blood", e);
			}

		} else {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Field are in invalid form or empty");
		}
		return response;

	}

	// ---------------for user to view his profile------------------
	@Override
	public Registration viewProfile(long phNo) throws BloodBankException {

		Registration registration = new Registration();
		Iterator<Registration> itr = null;

		if (phNo != 0) {
			try {
				itr = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getRegistrationCol())
						.aggregate("{$match:{phNo:#}}", phNo).and("{$project:{id:0,password:0}}")
						.as(Registration.class);
				if (itr.hasNext()) {
					registration = itr.next();
				}

			} catch (Exception e) {
				throw new BloodBankException("Exception occurred while fetching profile", e);
			}
		} else {
			registration.setStatusCode(ResponseConstants.Error_code);
			registration.setStatusMessage("Field are in invalid form or empty");
		}
		return registration;
	}

	// ---------------for user to edit their profile------------
	@Override
	public Response profileEdit(Registration updation, long id) throws BloodBankException {

		Response response = new Response();
		if (id != 0) {
			try {

				Registration dbProfile = new Jongo(DB_Constants.getMongodbDatabase())
						.getCollection(DB_Constants.getRegistrationCol()).findOne("{id:#}", id).as(Registration.class);
				updation.setId(id);
				if (dbProfile.getPhNo() == updation.getPhNo()) {
					new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getRegistrationCol())
							.update("{id:#}", id).upsert().with(updation);
					response.setStatusCode(ResponseConstants.Success_code);
					response.setStatusMessage("successfully edited profile");
				} else {
					Registration bg = new Jongo(DB_Constants.getMongodbDatabase())
							.getCollection(DB_Constants.getRegistrationCol()).findOne("{phNo:#}", updation.getPhNo())
							.as(Registration.class);
					if (bg == null) {
						new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getRegistrationCol())
								.update("{id:#}", id).upsert().with(updation);
						Transaction temp=new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getTransactionCol())
								.findOne("{phNo:#}",dbProfile.getPhNo()).as(Transaction.class);
						if(temp!=null) {
						new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getTransactionCol())
								.update("{phNo:#}", dbProfile.getPhNo()).upsert().multi().with("{$set:{phNo:#}}", updation.getPhNo());
						}
						response.setStatusCode(ResponseConstants.Success_code);
						response.setStatusMessage("successfully edited profile");
					} else {
						response.setStatusCode(ResponseConstants.Error_code);
						response.setStatusMessage("Failed to update as phone no already exist");
					}
				}
			} catch (Exception e) {
				logger.error("Exception Occured");
				throw new BloodBankException("Exception occured while editing profile", e);
			}
		} else {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Field are in invalid form or empty");
		}

		return response;
	}

	// -------------------for user to change their password------------
	@Override
	public Response changePassword(String newPassword, String oldPassword, long phNo) throws BloodBankException {

		Response response = new Response();
		if ((newPassword != null) && (newPassword != "") && (oldPassword != null) && (oldPassword != "")
				&& (phNo != 0)) {
			try {
				Registration dbprofile = new Jongo(DB_Constants.getMongodbDatabase())
						.getCollection(DB_Constants.getRegistrationCol()).findOne("{phNo:#}", phNo)
						.as(Registration.class);
				if (dbprofile != null && oldPassword.equals(dbprofile.getPassword())) {
					dbprofile.setPassword(newPassword);
					new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getRegistrationCol())
							.update("{phNo:#}", phNo).upsert().with(dbprofile);
					response.setStatusCode(ResponseConstants.Success_code);
					response.setStatusMessage("password updated successfully");
				} else {
					response.setStatusCode(ResponseConstants.Error_code);
					response.setStatusMessage("failed to update password");
				}
			} catch (Exception e) {
				throw new BloodBankException("Exception occured while changing password", e);
			}
		} else {
			response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Field are in invalid form or empty");
		}

		return response;
	}
}
