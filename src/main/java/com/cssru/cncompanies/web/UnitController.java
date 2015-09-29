package com.cssru.cncompanies.web;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.secure.Role;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.dto.UnitDto;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.AccountService;
import com.cssru.cncompanies.service.UnitService;

@Controller
public class UnitController {
	@Autowired
	private HumanService humanService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginChecker loginChecker;

	@Autowired
	private UnitService unitService;

	@Autowired
	private CompanyService companyService;

	@RequestMapping(value = "/unit.add/{companyId}", method = RequestMethod.GET)
	public String displayAddUnitForm(@PathVariable("companyId") Long companyId, Model model) {

		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		// проверяем, существует ли компания с данным идентификатором, которой владеет зарегистрированный пользователь
		Company company = companyService.getCompany(companyId, managerLogin);

		if (company == null) {
			return "redirect:/logout";
		}
		Unit unit = new Unit();
		unit.setCompany(company);

		Map<Long, String> humans = new LinkedHashMap<Long, String>();
		List<Human> humansFromCompany = humanService.listHuman(company, managerLogin);

		humans.put(0L, "не определен");
		for (Human h:humansFromCompany) {
			humans.put(h.getId(), h.getFullName());
		}

		model.addAttribute("humanList", humans);			
		model.addAttribute("company", company);			
		model.addAttribute("unitProxy", new UnitDto(unit));
		return "new_unit_form";
	}

	@RequestMapping(value = "/unit.add/{companyId}", method = RequestMethod.POST)
	public String addUnit(@PathVariable("companyId") Long companyId, @ModelAttribute("unitDto") UnitDto unitDto, BindingResult result, 
			Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		Company company = companyService.getCompany(companyId, managerLogin);

		Human owner;
		if (unitDto.getOwnerId() == 0L) {
			owner = null;
		} else {
			owner = humanService.getHuman(unitDto.getOwnerId(), managerLogin);
		}

		if (company != null) {
			Unit unit = new Unit();
			unit.setCompany(company);
			unit.setName(unitDto.getName());
			unit.setDescription(unitDto.getDescription());
			unit.setOwner(owner);
			unitService.addUnit(unit, managerLogin);
		} else {
			return "redirect:/logout";
		}

		return "redirect:/unit.list/"+company.getId();
	}

	// метод возвращает список подразделений, если компания известна
	@RequestMapping(value = "/unit.list/{companyId}")
	public String listUnit(@PathVariable("companyId") Long companyId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		// проверяем, существует ли компания с данным идентификатором, которой владеет зарегистрированный пользователь
		Company company = companyService.getCompany(companyId, managerLogin);

		if (company != null) {
			model.addAttribute("company", company);
			model.addAttribute("listUnit", unitService.listUnit(company, managerLogin));
		} else {
			return "redirect:/logout";
		}
		return "list_unit";
	}

	// метод возвращает список подразделений, подчиненных зарегистрированному пользователю 
	@RequestMapping(value = "/unit.list")
	public String listUnit(Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		model.addAttribute("listUnit", unitService.listUnitsWithOwner(managerLogin.getHuman(), managerLogin));

		return "list_unit";
	}

	// метод возвращает список всех подразделений из всех компаний 
	@RequestMapping(value = "/unit.list/all")
	public String listAllUnits(Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		if (!loginChecker.hasRole(Role.COMPANY_MANAGER)) {
			model.addAttribute("error", "Для этого действия нужно быть руководителем компании");
			return "error_page";
		}
		
		model.addAttribute("listUnit", unitService.listAllUnits(managerLogin));

		return "list_unit_all";
	}

	@RequestMapping("/unit.delete/{unitId}")
	public String deleteUnit(@PathVariable("unitId") Long unitId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		Unit existingUnit = unitService.getUnit(unitId, managerLogin);
		if (existingUnit == null) {
			return "redirect:/logout";
		}

		unitService.removeUnit(existingUnit, managerLogin);

		return "redirect:/unit.list/"+existingUnit.getCompany().getId();
	}

	@RequestMapping(value = "/unit.edit/{unitId}", method = RequestMethod.GET)
	public String editUnit(@PathVariable("unitId") Long unitId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		Unit existingUnit = unitService.getUnit(unitId, managerLogin);	
		if (existingUnit == null) {
			return "redirect:/logout";
		}

		Map<Long, String> humans = new LinkedHashMap<Long, String>();
		List<Human> humansFromCompany = humanService.listHuman(existingUnit.getCompany(), managerLogin);

		humans.put(0L, "не определен");
		for (Human h:humansFromCompany) {
			humans.put(h.getId(), h.getFullName());
		}

		model.addAttribute("humanList", humans);			
		model.addAttribute("company", existingUnit.getCompany());			
		UnitDto unitProxy = new UnitDto(existingUnit);
		model.addAttribute("unitProxy", unitProxy);
		return "edit_unit_form";
	}

	@RequestMapping(value = "/unit.edit", method = RequestMethod.POST)
	public String updateUnit(@ModelAttribute("unitProxy") UnitDto unitProxy, BindingResult result, 
			Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}
		
		// проверяем, существует ли компания с данным идентификатором, которой владеет зарегистрированный пользователь
		Company company = companyService.getCompany(unitProxy.getCompanyId(), managerLogin);
		Human owner;
		if (unitProxy.getOwnerId().longValue() == 0L) {
			owner = null;
		} else {
			owner = humanService.getHuman(unitProxy.getOwnerId(), managerLogin);
		}

		if (company != null) {
			Unit unit = new Unit();
			unit.setId(unitProxy.getId());
			unit.setCompany(company);
			unit.setName(unitProxy.getName());
			unit.setDescription(unitProxy.getDescription());
			unit.setOwner(owner);
			unitService.updateUnit(unit, managerLogin);
		} else {
			return "redirect:/logout";
		}
		return "redirect:/unit.list/"+company.getId();
	}
}
