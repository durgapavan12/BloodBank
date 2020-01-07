package com.bloodbankapp.dao;

import com.bloodbankapp.pojos.BloodGroup;
import com.bloodbankapp.pojos.Login;
import com.bloodbankapp.pojos.Registration;
import com.bloodbankapp.pojos.Response;

public interface AccountDao {

	Response registration(Registration registration);
	Response loginCheck(Login login);
	Response insertBGData(BloodGroup bloodGroup);
}
