package com.cssru.cncompanies.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Role;
import com.cssru.cncompanies.secure.BadLoginException;
import com.cssru.cncompanies.secure.LoginChecker;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.AccountService;

@Controller
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginChecker loginChecker;

	@Autowired
	private HumanService humanService;

	@RequestMapping(value = "/company.add", method = RequestMethod.GET)
	public String displayAddCompanyForm(Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		if (!loginChecker.hasRole(Role.COMPANY_MANAGER)) {
			return "redirect:/logout";
		}

		model.addAttribute("company", new Company());
		return "new_company_form";
	}

	@RequestMapping(value = "/company.add", method = RequestMethod.POST)
	public String addCompany(@ModelAttribute("company") Company company, BindingResult result, Model model) {

		if (!loginChecker.hasRole(Role.COMPANY_MANAGER)) {
			return "redirect:/logout";
		}

		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		company.setOwner(managerLogin.getHuman());
		companyService.addCompany(company, managerLogin);

		model.addAttribute("company", company);
		return "redirect:/company.list";
	}

	@RequestMapping(value = "/company.list")
	public String listCompany(Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		model.addAttribute("owner", managerLogin);
		model.addAttribute("listCompany", companyService.listCompany(managerLogin));
		return "list_company";
	}

	@RequestMapping("/company.delete/{companyId}")
	public String deleteCompany(@PathVariable("companyId") Long companyId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		companyService.removeCompany(companyId, managerLogin);
		return "redirect:/company.list";
	}

	@RequestMapping(value = "/company.edit/{companyId}", method = RequestMethod.GET)
	public String editCompany(@PathVariable("companyId") Long companyId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		Company company = companyService.getCompany(companyId, managerLogin);

		if (company != null) {
			model.addAttribute("company", company);
			return "edit_company_form";
		} else {
			return "redirect:/logout";
		}
	}

	@RequestMapping(value = "/company.edit", method = RequestMethod.POST)
	public String updateCompany(@ModelAttribute("company") Company company, BindingResult result, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		company.setOwner(managerLogin.getHuman());
		companyService.updateCompany(company, managerLogin);
		return "redirect:/company.list";
	}
}
