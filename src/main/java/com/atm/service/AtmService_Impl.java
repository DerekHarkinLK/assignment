package com.atm.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.atm.model.Account;
import com.atm.model.Atm;
import com.atm.model.Transaction;
import com.atm.validator.AccountException;
import com.atm.validator.AtmException;
import com.atm.validator.AtmMessages;
import com.atm.validator.BankException;
import com.atm.validator.BankNoteException;
import com.atm.validator.TransactionException;
import com.atm.validator.TransactionValidator;
import com.load.LoadManager;

@Service
public class AtmService_Impl extends AtmService_Tmpl {

	public AtmService_Impl() {
		super();
	}

	@Override
	protected boolean verifyLogin_Impl(Account account) throws Exception {

		boolean validUser = false;
		Collection<Account> accountList = new ArrayList<Account>();
		accountList = LoadManager.getInstance().getAccounts();

		for (Account acc : accountList) {
			if (account.getAccountNumber() == acc.getAccountNumber()) {
				if (account.getPin() == acc.getPin()) {
					validUser = true;
				}
			}
		}

		return validUser;
	}

	@Override
	protected Account checkBalance_Impl(Account account) {

		Collection<Account> accountList = new ArrayList<Account>();
		accountList = LoadManager.getInstance().getAccounts();

		for (Account acc : accountList) {

			if ((account.getAccountNumber() == acc.getAccountNumber()) && (account.getPin() == acc.getPin())) {
				account.setBalance(acc.getBalance());
				account.setOverDraftLimit(acc.getOverDraftLimit());
			}
		}

		return account;
	}

	@Override
	public Transaction withdrawFunds_Impl(Account account, Transaction trans) throws BankException {

		Collection<Account> accountList = new ArrayList<Account>();
		accountList = LoadManager.getInstance().getAccounts();
		Atm atm = LoadManager.getInstance().getAtm();

		Account userAccount = new Account();
		for (Account acc : accountList) {

			if (account.getAccountNumber() == acc.getAccountNumber()) {
				userAccount = acc;

				if (TransactionValidator.validateTotalAtmFunds(trans.getWithdrawalAmount(), atm)) {
					throw new AtmException(AtmMessages.ATMFAILURE);
				}
				else if (TransactionValidator.validateAccountFunds(trans.getWithdrawalAmount(), userAccount)) {
					throw new AccountException(AtmMessages.ACCOUNTFAILURE);
				}
				else if (TransactionValidator.validateTransactionMod(trans.getWithdrawalAmount())) {
					throw new TransactionException(AtmMessages.NOTEFAILURE);
				}
				else {
					HashMap<Object, Integer> transactionMap = new HashMap<Object, Integer>();
					transactionMap = withDraw(acc, trans, atm);

					if (transactionMap.get(50) != null) {
						trans.setNote50(transactionMap.get(50));
					}
					if (transactionMap.get(20) != null) {
						trans.setNote20(transactionMap.get(20));
					}
					if (transactionMap.get(10) != null) {
						trans.setNote10(transactionMap.get(10));
					}
					if (transactionMap.get(5) != null) {
						trans.setNote5(transactionMap.get(5));
					}

					trans.setStatus(0);
				}
			}
		}

		return trans;
	}

	private HashMap<Object, Integer> withDraw(Account userAccount, Transaction trans, Atm atm) throws BankException {

		int noOfFiftys = atm.getNumOfFiftys();
		int noOfTwentys = atm.getNumOfTwentys();
		int noOfTens = atm.getNumOfTens();
		int noOfFives = atm.getNumOfFives();

		HashMap<Object, Integer> transactionMap = new HashMap<Object, Integer>();
		transactionMap.put("remainingCash", trans.getWithdrawalAmount());

		if (transactionMap.get("remainingCash") >= 50 && noOfFiftys > 0) {
			transactionMap = cashDispensed(noOfFiftys, 50, transactionMap);
		}
		if (transactionMap.get("remainingCash") >= 20 && noOfTwentys > 0) {
			transactionMap = cashDispensed(noOfTwentys, 20, transactionMap);
		}
		if (transactionMap.get("remainingCash") >= 10 && noOfTens > 0) {
			transactionMap = cashDispensed(noOfTens, 10, transactionMap);
		}
		if (transactionMap.get("remainingCash") >= 5 && noOfFives > 0) {
			transactionMap = cashDispensed(noOfFives, 5, transactionMap);
		}
		if (transactionMap.get("remainingCash") > 0) {
			throw new BankNoteException(AtmMessages.ATMNOTESFAILURE);
		}

		persistTransaction(userAccount, trans.getWithdrawalAmount(), atm, transactionMap);

		return transactionMap;
	}

	private HashMap<Object, Integer> cashDispensed(int noOfNotes, int noteDenominator,
			HashMap<Object, Integer> transactionMap) {
		int notesDispensed = 0;
		int remainingCash = transactionMap.get("remainingCash");

		if (remainingCash > 0) {
			if (noOfNotes > 0) {
				if ((noOfNotes * noteDenominator) >= remainingCash) {
					notesDispensed = remainingCash / noteDenominator;
					remainingCash = remainingCash - (notesDispensed * noteDenominator);
					transactionMap.put(noteDenominator, notesDispensed);
					transactionMap.put("remainingCash", remainingCash);
				}
				else if ((noOfNotes * noteDenominator) < remainingCash) {
					notesDispensed = noOfNotes;
					remainingCash = remainingCash - (notesDispensed * noteDenominator);
					transactionMap.put(noteDenominator, notesDispensed);
					transactionMap.put("remainingCash", remainingCash);
				}
			}
		}

		return transactionMap;
	}

	private void persistTransaction(Account userAccount, int cashRequest, Atm atm,
			HashMap<Object, Integer> transactionMap) throws BankException {
		int original50s = atm.getNumOfFiftys();
		int original20s = atm.getNumOfTwentys();
		int original10s = atm.getNumOfTens();
		int original5s = atm.getNumOfFives();
		int originalAtmCash = atm.getTotalCash();
		int originalUserBal = userAccount.getBalance();

		try {
			for (Map.Entry<Object, Integer> set : transactionMap.entrySet()) {
				switch (String.valueOf(set.getKey())) {
				case "50":
					atm.setNumOfFiftys(atm.getNumOfFiftys() - set.getValue());
					break;
				case "20":
					atm.setNumOfTwentys(atm.getNumOfTwentys() - set.getValue());
					break;
				case "10":
					atm.setNumOfTens(atm.getNumOfTens() - set.getValue());
					break;
				case "5":
					atm.setNumOfFives(atm.getNumOfFives() - set.getValue());
				}
			}
			atm.setTotalCash(atm.getTotalCash() - cashRequest);
			userAccount.setBalance(userAccount.getBalance() - cashRequest);
		}
		catch (Exception e) {
			atm.setNumOfFiftys(original50s);
			atm.setNumOfTwentys(original20s);
			atm.setNumOfTens(original10s);
			atm.setNumOfFives(original5s);
			atm.setTotalCash(originalAtmCash);
			userAccount.setBalance(originalUserBal);

			throw new TransactionException(AtmMessages.TRANSACTIONFAILURE);
		}

	}

}
