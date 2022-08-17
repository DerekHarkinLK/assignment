package com.atm.service;

import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.validator.BankException;

public interface AtmService {

	public abstract boolean verifyLogin(Account account) throws Exception;

	public abstract Account checkBalance(Account account) throws Exception;

	public abstract Transaction withdrawFunds(Account account, Transaction transaction) throws BankException;

}
