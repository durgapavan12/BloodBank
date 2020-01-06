package com.bloodbankapp.constants;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DB_Constants {

private static final String MONGODB_HOST = "localhost";
private static final int MONGODB_PORT = 27017;
@SuppressWarnings("deprecation")
private static final DB MONGODB_DATABASE = new MongoClient(MONGODB_HOST,MONGODB_PORT).getDB("BloodBank");
public static DB getMongodbDatabase() {
return MONGODB_DATABASE;
}
}
