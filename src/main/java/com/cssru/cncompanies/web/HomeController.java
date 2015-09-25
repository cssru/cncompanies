package com.cssru.cncompanies.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Role;
import com.cssru.cncompanies.secure.BadLoginException;
import com.cssru.cncompanies.secure.LoginChecker;
import com.cssru.cncompanies.service.AccountService;

@Controller
public class HomeController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginChecker loginChecker;

	@RequestMapping(value="/")
	public String start(Model model){
		Login login = loginChecker.getRegisteredLogin();
		if (login == null) {
			return "index";
		}
		try {
			loginChecker.checkLogin(login);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		if (!login.isConfirmed()) {
			model.addAttribute("login", login);
			return "confirm";
		}
		if (loginChecker.hasRole(Role.ADMIN)) {
			model.addAttribute("login", login);
			return "admin/index";
		}
		if (login != null) {
			model.addAttribute("login", login);
			model.addAttribute("employeesCount", accountService.getEmployeesCount(login));
		}
		return "index";
	}

	@RequestMapping(value="/badlogin")
	public String badLogin(Model model){
		model.addAttribute("error", "Неправильное имя пользователя или пароль");
		model.addAttribute("wrong_credentials", true);
		return "error_page";
	}


	@RequestMapping(value="/admin/")
	public String startAdmin(Model model){
		if (loginChecker.hasRole(Role.ADMIN)) {
			model.addAttribute("login", loginChecker.getRegisteredLogin());
			return "admin/index";
		}
		return "redirect:/";
	}

	@RequestMapping(value="/chiefnotes")
	public String chiefNotesManual(){
		return "chiefnotes_manual";
	}
}
