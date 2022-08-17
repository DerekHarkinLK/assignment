package com.atm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.validator.BankException;

@RestController
public class AtmRestService {

	@Autowired
	AtmService atmService;

	@GetMapping({ "/balance" })
	public String balCheck(@RequestParam("accountNo") Integer accountNo, @RequestParam("pin") Integer pin)
			throws Exception {

		String returnMsg = "";

		try {

			Account acc = new Account(accountNo, pin);

			boolean login = atmService.verifyLogin(acc);
			if (login) {
				acc = atmService.checkBalance(acc);

				returnMsg = "Balance: " + Integer.toString(acc.getBalance()) + "\n";
				returnMsg = returnMsg + "Max withdrawal: " + Integer.toString(acc.getMaximumWithdrawal());
			}
			else {
				returnMsg = "Invalid Account No & pin";
			}

		}
		catch (BankException b) {
			returnMsg = b.getMessage();
		}

		return returnMsg;
	}

	@GetMapping({ "/withdraw" })
	public String withdrawCash(@RequestParam("accountNo") Integer accountNo, @RequestParam("pin") Integer pin,
			@RequestParam("cash") Integer cash) throws Exception {

		String returnMsg = "";

		try {
			Account acc = new Account(accountNo, pin);
			Transaction trans = new Transaction(accountNo, cash);

			boolean login = atmService.verifyLogin(acc);
			if (login) {
				trans = atmService.withdrawFunds(acc, trans);
				if (trans.getStatus() == 0) {
					acc = atmService.checkBalance(acc);

					returnMsg = "Success. Dispensed: ";

					int fiftys = 0;
					if (trans.getNote50() > 0) {
						fiftys = trans.getNote50();
						returnMsg = returnMsg + "&euro;50 - " + fiftys + ". ";
					}
					int twentys = 0;
					if (trans.getNote20() > 0) {
						twentys = trans.getNote20();
						returnMsg = returnMsg + "&euro;20 - " + twentys + ". ";
					}
					int tens = 0;
					if (trans.getNote10() > 0) {
						tens = trans.getNote10();
						returnMsg = returnMsg + "&euro;10 - " + tens + ". ";
					}
					int fives = 0;
					if (trans.getNote5() > 0) {
						fives = trans.getNote5();
						returnMsg = returnMsg + "&euro;5 - " + fives + ". ";
					}
					int userBal = 0;
					userBal = acc.getBalance();
					returnMsg = returnMsg + "Balance: &euro;" + userBal + ".";
				}
			}
			else {
				returnMsg = "Invalid Account No & pin";
			}
		}
		catch (BankException b) {
			returnMsg = b.getMessage();
		}

		return returnMsg;
	}

}
