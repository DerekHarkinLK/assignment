package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.validator.BankException;

public abstract class AtmService_Tmpl implements AtmService {

	public AtmService_Tmpl() {
		super();
	}

	public boolean verifyLogin(Account account) throws Exception {
		return verifyLogin_Impl(account);
	}

	abstract boolean verifyLogin_Impl(Account account) throws Exception;

	public Account checkBalance(Account account) throws Exception {
		return checkBalance_Impl(account);
	}

	abstract Account checkBalance_Impl(Account account) throws Exception;

	public Transaction withdrawFunds(Account account, Transaction transaction) throws BankException {
		return withdrawFunds_Impl(account, transaction);
	}

	abstract Transaction withdrawFunds_Impl(Account account, Transaction transaction) throws BankException;

}
