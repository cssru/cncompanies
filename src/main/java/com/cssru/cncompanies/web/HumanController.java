package com.cssru.cncompanies.web;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.secure.Role;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.dto.AccountRegisterDto;
import com.cssru.cncompanies.dto.ChangePasswordDto;
import com.cssru.cncompanies.proxy.HumanWrapper;
import com.cssru.cncompanies.proxy.PayProxy;
import com.cssru.cncompanies.secure.BadLoginException;
import com.cssru.cncompanies.secure.LoginChecker;
import com.cssru.cncompanies.service.CompanyService;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.AccountService;
import com.cssru.cncompanies.service.TaskService;
import com.cssru.cncompanies.service.UnitService;
import com.cssru.cncompanies.synch.SynchContainer;
import com.cssru.cncompanies.synch.SynchStatus;

@Controller
public class HumanController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginChecker loginChecker;

	@Autowired
	private HumanService humanService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	private String employeesCountErrorMessage = "Для подключенного тарифа достигнуто максимальное количество сотрудников. "
			+ "Для добавления большего количества сотрудников измените тариф."; 

	@RequestMapping(value = "/human.add/{unitId}")
	public String addHuman(@PathVariable("unitId") Long unitId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Unit managedUnit = unitService.getUnit(unitId, managerLogin);
		if (managedUnit == null) {
			return "redirect:/logout";
		}

		if (!checkEmployeesCount(managerLogin)) {
			model.addAttribute("unit", managedUnit);
			model.addAttribute("error", employeesCountErrorMessage);
			return "human_error_page";
		}

		model.addAttribute("unit", managedUnit);
		model.addAttribute("accountDto", new AccountRegisterDto());
		return "human.add";
	}

	// данные нового сотрудника введены в форму, получаем ее
	@RequestMapping(value = "/human.add/{unitId}", method = RequestMethod.POST)
	public String addHuman(@PathVariable("unitId") Long unitId, @ModelAttribute @Valid AccountRegisterDto accountDto, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			return null;
		}
		
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			result.rejectValue("login","login.no_rights", "У вас нет прав для добавления сотрудника");
			return null;
		}

		Unit managedUnit = unitService.getUnit(unitId, managerLogin);
		if (managedUnit == null) {
			return "redirect:/logout";
		}

		if (!checkEmployeesCount(managerLogin)) {
			result.rejectValue("login","login.too_many_employees", employeesCountErrorMessage);
			return null;
		}

		Login existingLogin = accountService.getLogin(accountDto.getLogin());
		if (existingLogin != null || accountDto.getLogin().equalsIgnoreCase("admin")) {
			// login already exists
			result.rejectValue("login","login.already_exists", "Указанный логин уже существует в системе");
			return null;
		}
		
		accountService.createLogin(accountDto, managedUnit);

		return "redirect:/human.list/"+managedUnit.getId();

	}

	@RequestMapping(value = "/human.profile", method = RequestMethod.GET)
	public String profile(Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human owner = managerLogin.getHuman();

		if (owner == null) {
			return "redirect:/logout";
		}

		model.addAttribute("human", owner);
		return "profile_form";	
	}

	@RequestMapping(value = "/human.profile", method = RequestMethod.POST)
	public String saveProfile(@ModelAttribute("human") Human human, BindingResult result, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		if (!managerLogin.getHuman().equals(human)) {
			model.addAttribute("error", "Доступ запрещен");
			return "error_page";
		}

		human.setUnit(managerLogin.getHuman().getUnit());
		humanService.updateHuman(human, managerLogin);

		return "redirect:/";
	}

	@RequestMapping(value = "/human.change_password/{humanId}", method = RequestMethod.GET)
	public String changePassword(@PathVariable("humanId") Long humanId, Model model) {
		Login ownerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(ownerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}		

		Login loginToChange;
		if (humanId == 0L) {
			loginToChange = ownerLogin;
		} else {
			Human human = humanService.getHuman(humanId, ownerLogin);
			if (human == null) {
				model.addAttribute("error", "Доступ запрещен");
				return "error_page";
			}
			loginToChange = accountService.getLogin(human);
			if (loginToChange == null) {
				model.addAttribute("error", "Аккаунт не существует");
				return "error_page";
			}
		}

		model.addAttribute("oldPasswordNeeded", humanId == 0L);
		model.addAttribute("passwordProxy", new ChangePasswordDto(loginToChange));
		return "change_password_form";	
	}

	@RequestMapping(value = "/human.change_password", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute("passwordProxy") ChangePasswordDto passwordProxy, BindingResult result, Model model) {
		Login loginToChange;
		Human humanToChange;
		Login ownerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(ownerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		if (!ownerLogin.getLogin().equals(passwordProxy.getLoginName())) {
			// change another user's password
			loginToChange = accountService.getLogin(passwordProxy.getLoginName());
			if (loginToChange == null) {
				return "redirect:/logout";
			}
			humanToChange = humanService.getHuman(loginToChange.getHuman().getId(), ownerLogin);
			if (humanToChange == null) {
				return "redirect:/logout";
			}
		} else {
			loginToChange = ownerLogin;
		}


		if (loginToChange.getLogin().equals(ownerLogin.getLogin()))
			if (!passwordEncoder.matches(passwordProxy.getOldPassword(), loginToChange.getPassword())) {
				model.addAttribute("error", "Введен неверный действующий пароль!");
				model.addAttribute("oldPasswordNeeded", true);
				model.addAttribute("passwordProxy", new ChangePasswordDto(ownerLogin));
				return "change_password_form";	
			}

		if (!passwordProxy.getNewPassword().equals(passwordProxy.getNewPassword2())) {
			model.addAttribute("error", "Пароли не совпадают!");
			model.addAttribute("oldPasswordNeeded", loginToChange.getLogin().equals(ownerLogin.getLogin()));
			model.addAttribute("passwordProxy", new ChangePasswordDto(ownerLogin));
			return "change_password_form";	
		}

		loginToChange.setPassword(passwordProxy.getNewPassword());
		accountService.updateLogin(loginToChange, true); // изменяем пароль, сервис зашифрует его сам

		model.addAttribute("human", loginToChange.getHuman());
		return "profile_form";
	}

	@RequestMapping(value = "/human.edit/{humanId}", method = RequestMethod.GET)
	public String editHuman(@PathVariable("humanId") Long humanId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human human = humanService.getHuman(humanId, managerLogin);
		if (human == null) {
			return "redirect:/logout";
		}
		Login humanLogin = accountService.getLogin(human);

		Company company = human.getUnit().getCompany();
		if (company != null) {
			Map<Long, String> units = new LinkedHashMap<Long, String>();
			List<Unit> unitsFromCompany = unitService.listUnit(company, managerLogin);
			if (unitsFromCompany != null) {
				for (Unit u:unitsFromCompany) {
					units.put(u.getId(), u.getName());
				}
				model.addAttribute("unitsList", units);
			}
		}
		model.addAttribute("humanProxy", new AccountRegisterDto(humanLogin));
		return "edit_human_form";
	}

	@RequestMapping(value = "/human.edit", method = RequestMethod.POST)
	public String updateHuman(@ModelAttribute("humanProxy") AccountRegisterDto humanProxy, BindingResult result,
			Model model) {

		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human existingHuman = humanService.getHuman(humanProxy.getId(), managerLogin);

		Unit existingUnit = unitService.getUnit(humanProxy.getUnitId(), managerLogin);

		if (existingHuman == null || existingUnit == null) {
			return "redirect:/logout";
		} else {
			existingHuman.setSurname(humanProxy.getSurname());
			existingHuman.setName(humanProxy.getName());
			existingHuman.setLastName(humanProxy.getLastName());
			existingHuman.setNote(humanProxy.getNote());
			existingHuman.setUnit(existingUnit);
			humanService.updateHuman(existingHuman, managerLogin);
		}

		return "redirect:/human.list/"+existingHuman.getUnit().getId();
	}

	// метод возвращает список сотрудников конкретного подразделения, подчиненных зарегистрированному пользователю
	@RequestMapping(value = "/human.list/{unitId}")
	public String listHuman(@PathVariable("unitId") Long unitId, Model model) {
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

		List<Human> list = null;
		list = humanService.listHuman(existingUnit, managerLogin);

		if (list == null) {
			return "redirect:/logout";
		}

		List<HumanWrapper> wrapperList = new ArrayList<HumanWrapper>(list.size());
		for (Human h:list) {
			wrapperList.add(new HumanWrapper(h, taskService, managerLogin));
		}

		model.addAttribute("unit", existingUnit);
		model.addAttribute("listHuman", wrapperList);
		return "list_human";
	}


	@RequestMapping("/human.delete/{humanId}")
	public String deleteHuman(@PathVariable("humanId") Long humanId, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human existingHuman = humanService.getHuman(humanId, managerLogin);
		if (existingHuman == null) {
			return "redirect:/logout";
		}
		humanService.removeHuman(humanId, managerLogin);
		return "redirect:/human.list/"+existingHuman.getUnit().getId();
	}


	// ADMIN METHODS
	@RequestMapping(value = "/admin/user.list")
	public String admListUser(Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}

		List<Login> list = null;
		list = accountService.admListUser();

		model.addAttribute("listUser", list);
		return "admin/list_user";
	}

	@RequestMapping(value = "/admin/user.change_password/{userId}", method = RequestMethod.GET)
	public String admChangeUserPassword(@PathVariable("userId") Long userId, Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}
		Login login = accountService.getLogin(userId);
		model.addAttribute("passwordProxy", new ChangePasswordDto(login));
		return "admin/change_password_form";	
	}

	@RequestMapping(value = "/admin/user.change_password", method = RequestMethod.POST)
	public String admChangeUserPassword(@ModelAttribute("passwordProxy") ChangePasswordDto passwordProxy, BindingResult result, Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}

		if (!passwordProxy.getNewPassword().equals(passwordProxy.getNewPassword2())) {
			model.addAttribute("error", "Пароли не совпадают!");
			passwordProxy.setNewPassword("");
			passwordProxy.setNewPassword2("");
			model.addAttribute("passwordProxy", passwordProxy);
			return "admin/change_password_form";	
		}

		Login login = accountService.getLogin(passwordProxy.getLoginName());

		login.setPassword(passwordProxy.getNewPassword());
		accountService.updateLogin(login, true); // изменяем пароль, сервис зашифрует его сам

		return "redirect:/admin/user.list";
	}

	@RequestMapping(value = "/admin/user.pay/{userId}", method = RequestMethod.GET)
	public String admUserPay(@PathVariable("userId") Long userId, Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}

		Login login = accountService.getLogin(userId);

		if (login.getTarif().getTarif() == Tarif.TARIF_TESTING) {
			model.addAttribute("login", login);
			return "admin/change_tarif_form";	
		}

		model.addAttribute("payProxy", new PayProxy(login.getId(), 0));
		model.addAttribute("login", login);
		return "admin/user_pay_form";	
	}

	@RequestMapping(value = "/admin/user.delete/{userId}", method = RequestMethod.GET)
	public String admUserDelete(@PathVariable("userId") Long userId, Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}

		Login login = accountService.getLogin(userId);

		if (login == null) {
			model.addAttribute("error", "Логин не существует");
			return "error_page";
		}
		
		accountService.removeUser(login);
		
		return "redirect:/admin/user.list";
	}

	@RequestMapping(value = "/admin/user.pay", method = RequestMethod.POST)
	public String admUserPay(@ModelAttribute("payProxy") PayProxy payProxy, BindingResult result, Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}

		Login login = accountService.getLogin(payProxy.getUserId());

		if (login.getTarif().getTarif() == Tarif.TARIF_TESTING) {
			model.addAttribute("login", login);
			return "admin/change_tarif_form";	
		}

		login.addPayment(payProxy.getPayValue());
		accountService.updateLogin(login, false);

		return "redirect:/admin/user.list";
	}

	@RequestMapping(value = "/admin/user.lock/{userId}/{locked}", method = RequestMethod.GET)
	public String admUserLock(@PathVariable("userId") Long userId, @PathVariable("locked") Integer locked, Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}

		Login userLogin = accountService.getLogin(userId);
		if (userLogin == null) {
			model.addAttribute("error", "Запрашиваемый аккаунт пользователя не существует");
			return "error_page";
		}

		userLogin.setLocked(locked != 0);
		accountService.updateLogin(userLogin, false);

		return "redirect:/admin/user.list";
	}

	@RequestMapping(value = "/admin/user.change_tarif/{loginId}", method = RequestMethod.GET)
	public String admChangeUserTarif(@PathVariable("loginId") Long loginId, Model model) {
		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}
		Login login = accountService.getLogin(loginId);
		Map<Integer, String> tarifs = new LinkedHashMap<Integer, String>();
		List<Tarif> tarifList = Tarif.getPossibleTarifs();
		if (login.getTarif().getTarif() != Tarif.TARIF_TESTING) {
			// it is impossible to change paid tarif to TARIF_TESTING
			tarifList.remove(new Tarif(Tarif.TARIF_TESTING));
		}
		for (Tarif t:tarifList) {
			tarifs.put(t.getTarif(), t.getTarifName());
		}
		model.addAttribute("tarifList", tarifs);

		model.addAttribute("login", login);
		model.addAttribute("tarif", login.getTarif());
		return "admin/change_tarif_form";	
	}

	@RequestMapping(value = "/admin/user.change_tarif/{loginId}", method = RequestMethod.POST)
	public String admChangeUserTarif(@PathVariable("loginId") Long loginId, 
			@ModelAttribute("tarif") Tarif tarif, 
			BindingResult result, 
			Model model) {

		if (!loginChecker.hasRole(Role.ADMIN)) {
			return "redirect:/logout";
		}

		Login login = accountService.getLogin(loginId);
		if (login == null || !Tarif.isTarifPossible(tarif) ||
				(login.getTarif().getTarif() != Tarif.TARIF_TESTING && tarif.getTarif() == Tarif.TARIF_TESTING)) {
			return "redirect:/logout";
		}

		login.setTarif(tarif);
		accountService.updateLogin(login, false); // не изменяем пароль

		return "redirect:/admin/user.list";
	}


	private boolean checkEmployeesCount(Login managerLogin) {
		Long employeesCount;
		Login companyOwnerLogin;
		if (loginChecker.hasRole(Role.COMPANY_MANAGER)) {
			// company manager tries add new employee
			companyOwnerLogin = managerLogin;
		} else
			if (loginChecker.hasRole(Role.UNIT_MANAGER)) {
				companyOwnerLogin = accountService.getLogin(managerLogin.getHuman().getUnit().getCompany().getOwner());
			} else {
				return false;
			}
		employeesCount = accountService.getEmployeesCount(companyOwnerLogin);
		return employeesCount < companyOwnerLogin.getTarif().getMaximumEmployees();
	}

	// RESTful methods

	@RequestMapping(value = "/human.synch", method=RequestMethod.POST, produces={"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody SynchContainer<Human> synchronizeHumans(@RequestBody SynchContainer<Human> clientRequest) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			SynchContainer<Human> errorContainer = new SynchContainer<Human>();
			switch (ble.getReason()) {
			case BadLoginException.ACCESS_DENIED: errorContainer.setSynchStatus(SynchStatus.ERROR_ACCESS_DENIED); break;
			case BadLoginException.LOGIN_EXPIRED: errorContainer.setSynchStatus(SynchStatus.ERROR_LOGIN_EXPIRED); break;
			case BadLoginException.LOGIN_LOCKED: errorContainer.setSynchStatus(SynchStatus.ERROR_LOGIN_LOCKED); break;
			case BadLoginException.LOGIN_NOT_CONFIRMED: errorContainer.setSynchStatus(SynchStatus.ERROR_LOGIN_NOT_CONFIRMED); break;
			}
			return errorContainer;
		}

		SynchContainer<Human> resp = humanService.synchronize(clientRequest, managerLogin); 
		return resp;
	}

}
