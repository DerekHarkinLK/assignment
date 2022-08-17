package com.atm.model;

public class Account {

	private int accountNumber;

	private int pin;

	private int balance;

	private int overDraft;

	public Account() {

	}

	public Account(int accountNumber, int pin) {
		this.accountNumber = accountNumber;
		this.pin = pin;
	}

	public Account(int accountNumber, int pin, int openingBalance, int overDraft) {
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.balance = openingBalance;
		this.overDraft = overDraft;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(int accNo) {
		this.accountNumber = accNo;
	}

	public int getPin() {
		return this.pin;
	}

	public void setPin(int p) {
		this.pin = p;
	}

	public int getBalance() {
		return this.balance;
	}

	public void setBalance(int bal) {
		this.balance = bal;
	}

	public int getOverDraftLimit() {
		return this.overDraft;
	}

	public void setOverDraftLimit(int overD) {
		this.overDraft = overD;
	}

	public int getMaximumWithdrawal() {
		int maxCash = 0;
		maxCash = getBalance() + getOverDraftLimit();
		return maxCash;
	}

}
