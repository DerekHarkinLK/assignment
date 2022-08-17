package com.load;

import java.util.ArrayList;
import java.util.Collection;

import com.atm.model.Account;
import com.atm.model.Atm;

public class LoadManager {

	private Collection<Account> accounts = new ArrayList<Account>();

	private Atm atm;

	private static LoadManager instance = null;

	public static LoadManager getInstance() {
		if (instance == null)
			instance = new LoadManager();

		return instance;
	}

	public LoadManager() {
		accounts.add(new Account(123456789, 1234, 800, 200));
		accounts.add(new Account(987654321, 4321, 1230, 150));
		atm = new Atm(1500, 10, 30, 30, 20);
	}

	public Collection<Account> getAccounts() {
		return accounts;
	}

	public Atm getAtm() {
		return atm;
	}

}
