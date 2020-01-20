package com.bloodbankapp.daoimplementation;


import org.jongo.Jongo;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.bloodbankapp.constants.DB_Constants;
import com.bloodbankapp.constants.ResponseConstants;
import com.bloodbankapp.dao.AdminDao;
import com.bloodbankapp.exception.BloodBankException;
import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Counter;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;
import com.bloodbankapp.pojos.Transaction;

@Repository("adminDao")
public class AdminDaoImplementation implements AdminDao {
	

	@Override
	public Login checkAdmin(Login login) throws BloodBankException{
		Login user = null;
		 if((login.getPhNo()!=0)&&(login.getPassword()!=null)&&(login.getPassword()!="")) {
		try {
		 user = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getAdminCol())
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
		} else {
			user.setStatusCode(ResponseConstants.Error_code);
			user.setStatusMessage("User Not found");
				
		}
		
		}catch (Exception e) {
			user.setStatusCode(ResponseConstants.Error_code);
			user.setStatusMessage("Exception Occured");
			throw new BloodBankException("Exception Occured inside implementation of AdminDao",e);
		}}else {
			user.setStatusCode(ResponseConstants.Error_code);
			user.setStatusMessage("Field are in invalid form or empty");
		}
		 return user;
	}


	//----------------------For admin to change cost and quantity----------------
		@Override
		public Response insertBGData(BloodGroup bloodGroup) throws BloodBankException{
						
			Response response = new Response();
			if((bloodGroup.getBloodGroup()!="")&&(bloodGroup!=null)) {
			try {
				BloodGroup bg = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getBloodgroupCol())
						.findOne("{bloodGroup:#}", bloodGroup.getBloodGroup()).as(BloodGroup.class);

				if (bg != null) {
					int n = bg.getQuantity() + bloodGroup.getQuantity();
					bg.setQuantity(n);
					int flag = 0;
					if (bloodGroup.getAmount() != bg.getAmount()&&bloodGroup.getAmount()>0) {
						bg.setAmount(bloodGroup.getAmount());
						response.setStatusMessage("amount changed!");
						if (bloodGroup.getQuantity()>0) {
							response.setStatusMessage("amount changed and quantity increased!");
						}
						flag = 1;
					}
					new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getBloodgroupCol())
							.update("{bloodGroup:#}", bloodGroup.getBloodGroup()).upsert().with(bg);
					response.setStatusCode(ResponseConstants.Success_code);
					if (flag == 0)
						response.setStatusMessage("Successfully blood group qty added!");
				} else {
					new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getBloodgroupCol())
							.insert(bloodGroup);
					response.setStatusCode(ResponseConstants.Success_code);
					response.setStatusMessage("Successfully inserted new bloodgroup!");
				}

			} catch (Exception e) {
				throw new BloodBankException("Error while inserting bloodgroup details");
			}
			}else {
				response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Field are in invalid form or empty");
			}
			return response;
		}


		//------------updating blood qty and transactions while donate/receive--------
		@Override
		public Response updateQuantity(Transaction transaction) throws BloodBankException{

			Response response = new Response();
			if((transaction.getBloodGroup()!=null)&&(transaction.getAmount()!=0)&&(transaction.getQuantity()!=0)&&(transaction.getBloodGroup()!="")) {

			try {
			BloodGroup bg = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getBloodgroupCol())
					.findOne("{bloodGroup:#}", transaction.getBloodGroup()).as(BloodGroup.class);

			if (bg != null) {
				int out=0;
				if (transaction.getStatus().equalsIgnoreCase("donated")) {
				int n = bg.getQuantity() + transaction.getQuantity();
				bg.setQuantity(n);}
				if (transaction.getStatus().equalsIgnoreCase("recieved")) {
					int n=bg.getQuantity()-transaction.getQuantity();
					
					if(n<0) {
						response.setStatusCode(ResponseConstants.Error_code);
						response.setStatusMessage("Out of stock!");
						out=1;
					}else
					bg.setQuantity(n);
				}
				new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getBloodgroupCol())
						.update("{bloodGroup:#}", transaction.getBloodGroup()).upsert().with(bg);
				insertTransaction(transaction);
				if(out==0) {
				response.setStatusCode(ResponseConstants.Success_code);
				response.setStatusMessage("Transactions and quantity updated");
				}
			} else {
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("failed to update blood quantity");
			}
			}catch (Exception e) {
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("Exception Occured");
				throw new BloodBankException("Exception occured while updating blood quantity and transactions",e);
			}}else {
				response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Field are in invalid form or empty");
			}

			return response;
		}
		
		
		//-----------------for insertion of transaction-------------------
		@Override
		public Response insertTransaction(Transaction transaction) throws BloodBankException{
			Response response = new Response();
			try {

				new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getCounterCol())
						.update("{ _id:'transactionID' }").with("{$inc:{seq:1}}");
				Counter counter = new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getCounterCol())
						.findOne("{ _id: 'transactionID'}").as(Counter.class);

				transaction.setT_id(counter.getSeq());

				new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getTransactionCol())
						.insert(transaction);
				response.setStatusCode(ResponseConstants.Success_code);
				response.setStatusMessage("Amount transfered!");
			}

			catch (Exception e) {
				response.setStatusCode(ResponseConstants.Error_code);
				throw new BloodBankException("Error while inserting amount",e);
			}
			return response;

		}
		
		//----------------for admin to delete particular user-----------
		@Override
		public Response deleteUser(long phNo) throws BloodBankException {
			Response response = new Response();
			if(phNo!=0) {
			
			try {
			Registration user = new Jongo(DB_Constants.getMongodbDatabase())
					.getCollection(DB_Constants.getRegistrationCol()).findOne("{phNo:#}", phNo).as(Registration.class);
			if (user != null) {
				new Jongo(DB_Constants.getMongodbDatabase()).getCollection(DB_Constants.getRegistrationCol())
						.remove("{phNo:#}", phNo);
				response.setStatusCode(ResponseConstants.Success_code);
				response.setStatusMessage("Deleted!");
			} else {
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("No user to delete!");
			}
			}catch (Exception e) {
				response.setStatusCode(ResponseConstants.Error_code);
				response.setStatusMessage("Error while inserting amount");
				throw new BloodBankException("Exception Occured While deleting user");
			}
			}else {
				response.setStatusCode(ResponseConstants.Error_code);
			response.setStatusMessage("Field are in invalid form or empty");
			}
			return response;
		}
}
