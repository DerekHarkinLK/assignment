package com.atm.model;

public class Transaction {

	private int withdrawalAmount;

	private int accountNumber;

	private int note50 = 0;

	private int note20 = 0;

	private int note10 = 0;

	private int note5 = 0;

	private int transactionStatus;

	public Transaction() {

	}

	public Transaction(int accountNumber, int withDrawAmount) {
		this.accountNumber = accountNumber;
		this.withdrawalAmount = withDrawAmount;
	}

	public int getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(int accountNo) {
		this.accountNumber = accountNo;
	}

	public int getNote50() {
		return this.note50;
	}

	public void setNote50(int note50) {
		this.note50 = note50;
	}

	public int getNote20() {
		return this.note20;
	}

	public void setNote20(int note20) {
		this.note20 = note20;
	}

	public int getNote10() {
		return this.note10;
	}

	public void setNote10(int note10) {
		this.note10 = note10;
	}

	public int getNote5() {
		return this.note5;
	}

	public void setNote5(int note5) {
		this.note5 = note5;
	}

	public int getWithdrawalAmount() {
		return this.withdrawalAmount;
	}

	public void setWithdrawalAmount(int withDrawAmount) {
		this.withdrawalAmount = withDrawAmount;
	}

	public int getStatus() {
		return this.transactionStatus;
	}

	public void setStatus(int transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

}
