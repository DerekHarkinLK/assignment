package com.atm.service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.atm.controller.AtmController;
import com.atm.model.Account;
import com.atm.model.Atm;
import com.atm.model.Transaction;
import com.atm.validator.AccountException;
import com.atm.validator.AtmException;
import com.atm.validator.BankNoteException;
import com.atm.validator.TransactionException;
import com.load.LoadManager;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;

@RunWith(SpringRunner.class)
@WebMvcTest(AtmController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTests {

	@Autowired
	AtmService atmService;

	@Before
	public void setup() {
		LoadManager lm = new LoadManager();
		System.out.println(lm.getInstance().getAtm().getTotalCash());
	}

	@Test
	@Order(1)
	public void validAccountPin_thenLoginShouldbeSuccessful() {
		try {

			Account acc = new Account();
			acc.setAccountNumber(123456789);
			acc.setPin(1234);

			assertEquals(true, atmService.verifyLogin(acc));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(2)
	public void invalidAccountPin_thenLoginShouldFail() {
		try {

			Account acc = new Account();
			acc.setAccountNumber(123451234);
			acc.setPin(7845);

			assertEquals(false, atmService.verifyLogin(acc));

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(3)
	public void checkBalance_thenReturnBalancePlusOverDraft() {
		try {

			Account acc = new Account();
			acc.setAccountNumber(987654321);
			acc.setPin(4321);

			acc = atmService.checkBalance(acc);

			assertEquals(1380, acc.getMaximumWithdrawal());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(4)
	public void checkBalance2_thenReturnBalancePlusOverDraft() {
		try {

			Account acc = new Account();
			acc.setAccountNumber(123456789);
			acc.setPin(1234);

			acc = atmService.checkBalance(acc);

			assertEquals(1000, acc.getMaximumWithdrawal());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	@Order(5)
	public void withdrawMoreThanMaxAtmFunds_thenThrowException() {
		Assertions.assertThrows(AtmException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				Transaction trans = new Transaction();
				int cashRequest = 2000;

				trans.setAccountNumber(123456789);
				trans.setWithdrawalAmount(cashRequest);

				Account acc = new Account();
				acc.setAccountNumber(123456789);
				acc.setPin(1234);

				trans = atmService.withdrawFunds(acc, trans);

			}
		});
	}

	@Test
	@Order(6)
	public void withdrawMoreThanMaxNotes_thenThrowException() {
		Assertions.assertThrows(BankNoteException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				Transaction trans = new Transaction();
				int cashRequest = 5;

				trans.setAccountNumber(123456789);
				trans.setWithdrawalAmount(cashRequest);

				Account acc = new Account();
				acc.setAccountNumber(123456789);
				acc.setPin(1234);

				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);
				trans = atmService.withdrawFunds(acc, trans);

			}
		});
	}

	@Test
	@Order(7)
	public void withdrawMoreThanUserBal_thenThrowException() {
		Assertions.assertThrows(AccountException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				Transaction trans = new Transaction();
				int cashRequest = 1400;

				trans.setAccountNumber(987654321);
				trans.setWithdrawalAmount(cashRequest);

				Account acc = new Account();
				acc.setAccountNumber(987654321);
				acc.setPin(4321);

				trans = atmService.withdrawFunds(acc, trans);
			}
		});
	}

	@Test
	@Order(8)
	public void withdrawNoteNotDivisibleBy5_thenThrowException() {
		Assertions.assertThrows(TransactionException.class, new Executable() {

			@Override
			public void execute() throws Throwable {

				Transaction trans = new Transaction();
				int cashRequest = 23;

				trans.setAccountNumber(987654321);
				trans.setWithdrawalAmount(cashRequest);

				Account acc = new Account();
				acc.setAccountNumber(987654321);
				acc.setPin(4321);

				trans = atmService.withdrawFunds(acc, trans);
			}
		});
	}

	@Test
	@Order(9)
	public void withdraw_thenUpdateTotalAtmCash() {
		try {

			Transaction trans = new Transaction();
			Atm atm = LoadManager.getInstance().getAtm();
			int atmBal = atm.getTotalCash();
			int cashRequest = 250;
			int remainingAtmBal = atmBal - cashRequest;

			trans.setAccountNumber(123456789);
			trans.setWithdrawalAmount(cashRequest);

			Account acc = new Account();
			acc.setAccountNumber(123456789);
			acc.setPin(1234);

			trans = atmService.withdrawFunds(acc, trans);

			assertEquals(remainingAtmBal, atm.getTotalCash());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
