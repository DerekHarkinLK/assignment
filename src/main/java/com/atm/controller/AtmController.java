package com.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.atm.model.Account;
import com.atm.model.Transaction;
import com.atm.service.AtmService;
import com.atm.validator.AtmMessages;
import com.atm.validator.BankException;

@Controller
public class AtmController {

	@Autowired
	AtmService atmService;

	@GetMapping({ "/LOGIN" })
	public ModelAndView login() {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/WEB-INF/views/login.jsp");

		Account acc = new Account();
		modelAndView.addObject("aAccount", acc);

		return modelAndView;
	}

	@GetMapping("/MENU")
	public ModelAndView mainMenu(@ModelAttribute("aAccount") Account account) throws Exception {
		boolean validUser = false;
		ModelAndView modelAndView = new ModelAndView();
		String view = "/WEB-INF/views/error.jsp";

		validUser = atmService.verifyLogin(account);

		if (validUser) {
			modelAndView.addObject("aAccount", account);
			view = "/WEB-INF/views/atmMenu.jsp";
		}
		else {
			modelAndView.addObject("errorMsg", AtmMessages.LOGINFAILURE);
		}
		modelAndView.setViewName(view);

		return modelAndView;
	}

	@GetMapping("/BALANCECHECK")
	public ModelAndView getBalance(@ModelAttribute("aAccount") Account account) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String view = "/WEB-INF/views/checkbalance.jsp";

		account = atmService.checkBalance(account);

		modelAndView.addObject("aAccount", account);

		modelAndView.setViewName(view);

		return modelAndView;
	}

	@GetMapping("/WITHDRAWAL")
	public ModelAndView getMenuWithdraw(@ModelAttribute("aAccount") Account account) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		String view = "/WEB-INF/views/withdraw.jsp";

		modelAndView.addObject("aAccount", account);

		Transaction transaction = new Transaction();
		modelAndView.addObject("aTransaction", transaction);

		modelAndView.setViewName(view);

		return modelAndView;
	}

	@PostMapping("/TRANSACTION")
	public ModelAndView getWithDraw(@ModelAttribute("aAccount") Account account, BindingResult resultAccount,
			@ModelAttribute("aTransaction") Transaction transaction, BindingResult resultTransaction) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		String view = "/WEB-INF/views/error.jsp";

		try {
			transaction = atmService.withdrawFunds(account, transaction);
			if (transaction.getStatus() == 0) {
				account = atmService.checkBalance(account);
				view = "/WEB-INF/views/dispensed.jsp";
				modelAndView.addObject("aTransaction", transaction);
				modelAndView.addObject("aAccount", account);
			}
		}
		catch (BankException b) {
			modelAndView.addObject("errorMsg", b.getMessage());
		}

		modelAndView.setViewName(view);

		return modelAndView;
	}

}
