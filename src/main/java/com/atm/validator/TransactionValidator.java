package com.atm.validator;

import com.atm.model.Account;
import com.atm.model.Atm;

public class TransactionValidator {

	public static boolean validateTotalAtmFunds(int cashRequest, Atm atm) {

		if (atm.getTotalCash() < cashRequest) {
			return true;
		}
		return false;
	}

	public static boolean validateAccountFunds(int cashRequest, Account acc) {
		int totalAvailableFunds = 0;
		totalAvailableFunds = acc.getBalance() + acc.getOverDraftLimit();

		if (cashRequest > totalAvailableFunds) {
			return true;
		}
		return false;
	}

	public static boolean validateTransactionMod(int cashRequest) {

		if ((cashRequest % 5) != 0) {
			return true;
		}
		return false;
	}

}
