package com.bloodbankapp.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BloodBankException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public BloodBankException(String message) {
	logger.error(message);
	}

	public BloodBankException(Throwable e) {
		super(e);
	}

	public BloodBankException(String messsage, Throwable e) {
		super(messsage, e);
	}
}
