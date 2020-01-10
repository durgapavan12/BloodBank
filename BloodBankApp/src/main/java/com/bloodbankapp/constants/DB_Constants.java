package com.bloodbankapp.constants;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DB_Constants {

private static final String MONGODB_HOST = "localhost";
private static final String COUNTER_COL= "Counter";
private static final String DATABASE_NAME="BloodBank";
private static final String REGISTRATION_COL="registration";
private static final String ADMIN_COL="admin";
private static final String BLOODGROUP_COL="bloodGroups";
private static final String TRANSACTION_COL="transaction";
private static final int MONGODB_PORT = 27017;
private static final DB MONGODB_DATABASE = new MongoClient(MONGODB_HOST,MONGODB_PORT).getDB(DATABASE_NAME);

public static DB getMongodbDatabase() {
return MONGODB_DATABASE;
}
public static String getMongodbHost() {
	return MONGODB_HOST;
}
public static String getRegistrationCol() {
	return REGISTRATION_COL;
}
public static String getBloodgroupCol() {
	return BLOODGROUP_COL;
}
public static String getTransactionCol() {
	return TRANSACTION_COL;
}
public static int getMongodbPort() {
	return MONGODB_PORT;
}
public static String getCounterCol() {
	return COUNTER_COL;
}
public static String getAdminCol() {
	return ADMIN_COL;
}
}