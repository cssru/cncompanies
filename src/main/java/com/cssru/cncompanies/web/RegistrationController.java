package com.cssru.cncompanies.web;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.dto.AccountDto;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.LoginService;

@Controller
public class RegistrationController {

	@Value("${mail.login}")
	private String mailLogin;
	
	@Autowired
	HumanService humanService;

	@Autowired
	LoginService loginService;

	@Autowired
	MailSender mailSender;

	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public AccountDto startRegistration(){
		AccountDto accountDto = new AccountDto();
		return accountDto;
	}

	@RequestMapping(value="/registration", method=RequestMethod.POST)
	public String register(@ModelAttribute @Valid AccountDto accountDto, BindingResult result, Model model){

		if (result.hasErrors()) {
			return null;
		}
		
		Login existingLogin = loginService.getLogin(accountDto.getLogin());
		if (existingLogin != null || accountDto.getLogin().equalsIgnoreCase("admin")) {
			// login already exists
			result.rejectValue("login","already_exists");
			return null;
		}

		Login newLogin = loginService.createLogin(accountDto);
		newLogin.setConfirmCode(generateConfirmCode());

		//send confirm code to email "newLogin.getEmail()"
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(newLogin.getEmail());
		msg.setFrom("cssru@mail.ru");
		msg.setText(
				"Для активации аккаунта на сайте chessoft.ru перейдите по ссылке "
						+ "http://chessoft.ru/confirm/" + newLogin.getId() + "/" + newLogin.getConfirmCode());
		try {
			// DEBUG ONLY!!!
			mailSender.send(msg);
			model.addAttribute("login", newLogin);
		}
		catch (MailException ex) {
			ex.printStackTrace();
			model.addAttribute("error", ex.getMessage());
			return "error_page";
		}
		return "index";
	}

	@RequestMapping(value="/confirm/{loginId}/{confirmCode}", method=RequestMethod.GET)
	public String confirm(@PathVariable("loginId") Long loginId, @PathVariable("confirmCode") String confirmCode, Model model) {
		if (confirmCode == null || confirmCode.length() == 0) {
			return "redirect:/logout";
		}

		Login login = loginService.getLogin(loginId);
		if (!login.isConfirmed() && confirmCode.equals(login.getConfirmCode())) {
			login.setConfirmCode(null);
			loginService.updateLogin(login, false);
			model.addAttribute("login", login);
			model.addAttribute("confirmed", true);
		}
		return "index";
	}

	@RequestMapping(value="/password_recovery")
	public String passwordRecovery(Model model){
		model.addAttribute("login", new Login());
		return "password_recovery_page";
	}

	@RequestMapping(value="/password_recovery", method=RequestMethod.POST)
	public String generateNewPassword(@ModelAttribute("login") Login login, BindingResult result, Model model){

		Login existingLogin = loginService.getLogin(login.getLogin());
		if (existingLogin == null || 
				!existingLogin.getEmail().equals(login.getEmail()) ||
				!existingLogin.getHuman().getSurname().equals(login.getHuman().getSurname()) ||
				!existingLogin.getHuman().getName().equals(login.getHuman().getName()) ||
				!existingLogin.getHuman().getLastName().equals(login.getHuman().getLastName()) ||
				login.getLogin().equalsIgnoreCase("admin")) {
			// login does not exists or account data is wrong
			model.addAttribute("error", "Введены неверные данные об аккаунте");
			return "password_recovery_page";	
		}

		String newPassword = generatePassword();
		//send new generated password to email "login.getEmail()"
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(login.getEmail());
		msg.setFrom(mailLogin);
		msg.setText("Ваш новый пароль для входа в систему "	+ newPassword);
		try{
			mailSender.send(msg);
			model.addAttribute("login", login);
			existingLogin.setPassword(newPassword);
			loginService.updateLogin(existingLogin, true);
		}
		catch (MailException ex) {
			ex.printStackTrace();
			model.addAttribute("error", ex.getMessage());
			return "error_page";
		}
		return "index";
	}


	private String generateConfirmCode() {
		return generateRandomString(40);
	}

	private String generatePassword() {
		return generateRandomString(8);
	}

	private String generateRandomString(int length) {
		final String abc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int index = (int) Math.round(Math.random()*(abc.length()-1));
			buffer.append(abc.charAt(index));
		}
		return buffer.toString();
	}
}
