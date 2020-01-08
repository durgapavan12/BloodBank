package com.bloodbankapp;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class BloodBankAppApplication {

//	private static final Logger logger= Logger.getLogger(BloodBankAppApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(BloodBankAppApplication.class, args);
	}

}
