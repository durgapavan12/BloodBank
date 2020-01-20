package com.bloodbankapp.exception;

public class BloodBankException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BloodBankException(String message) {
		super();
	}

	public BloodBankException(Throwable e) {
		super(e);
	}

	public BloodBankException(String messsage, Throwable e) {
		super(messsage, e);
	}
}
