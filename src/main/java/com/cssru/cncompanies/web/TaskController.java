package com.cssru.cncompanies.web;


import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.cssru.cncompanies.ajax.AjaxEditRequest;
import com.cssru.cncompanies.ajax.AjaxResponse;
import com.cssru.cncompanies.ajax.AjaxResponseCodes;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Task;
import com.cssru.cncompanies.dto.TaskDto;
import com.cssru.cncompanies.proxy.TaskJsonProxy;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.AccountService;
import com.cssru.cncompanies.service.TaskService;
import com.cssru.cncompanies.synch.SynchContainer;
import com.cssru.cncompanies.synch.SynchStatus;

@Controller
public class TaskController {
	@Autowired
	private AccountService accountService;

	@Autowired
	private LoginChecker loginChecker;

	@Autowired
	private HumanService humanService;

	@Autowired
	private TaskService taskService;

	@RequestMapping(value = "/task.add/{humanId}")
	public String displayAddTaskForm(@PathVariable("humanId") Long humanId, Model model) {

		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human executor = humanService.getHuman(humanId, managerLogin);
		if (executor == null) {
			return "redirect:/logout";
		}

		model.addAttribute("executor", executor);
		model.addAttribute("taskDto", new TaskDto());
		return "new_task_form";	
	}

	// данные нового сотрудника введены в форму, получаем ее
	@RequestMapping(value = "/task.add/{humanId}", method = RequestMethod.POST)
	public String addTask(@PathVariable("humanId") Long humanId, @ModelAttribute("taskDto") @Valid TaskDto taskDto, BindingResult result, Model model) {

		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human executor = humanService.getHuman(humanId, managerLogin);

		if (executor == null) {
			return "redirect:/logout";
		}

		if (result.hasErrors()) {
			model.addAttribute("taskDto", taskDto);
			model.addAttribute("executor", executor);
			return "new_task_form";
		}
		
		Task task = new Task();
		task.setComment(taskDto.getComment());
		task.setContent(taskDto.getContent());
		task.setExpires(new Date(taskDto.getExpiresMillis()));
		task.setAlertTime(taskDto.getAlertTime());
		taskService.addTask(task, executor, managerLogin);

		return "redirect:/task.list/"+ 
		(managerLogin.getHuman().equals(executor) ? 0 : executor.getId());

	}

	@RequestMapping(value = "/task.update/{taskId}/{redirect}", method = RequestMethod.POST)
	public String updateTask(@PathVariable("taskId") Long taskId, @PathVariable("redirect") String redirect, @ModelAttribute("taskProxy") TaskDto taskProxy, BindingResult result, 
			Model model) {

		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Task task = taskService.getTask(taskId, managerLogin);

		if (task == null || task.getId() != taskProxy.getId()) {
			return "redirect:/logout";
		}

		task.setContent(taskProxy.getContent());
		task.setComment(taskProxy.getComment());
		task.setDifficulty(taskProxy.getDifficulty());
		task.setExpires(new Date(taskProxy.getExpiresMillis()));
		task.setAlertTime(taskProxy.getAlertTime());
		
		if ((task.getDone() != null) ^ (taskProxy.isDone())) {
			if (taskProxy.isDone()) {
				task.setDone(new Date());
			} else {
				task.setDone(null);
			}
		}

		taskService.updateTask(task, managerLogin);

		if ("slave".equals(redirect)) { // must return to slave tasks page
			return "redirect:/task.list/slave";
		}

		if ("slave-done".equals(redirect)) { // must return to slave tasks page
			return "redirect:/task.list/slave/done";
		}

		try {
			Long humanId = Long.parseLong(redirect);
			if (task.getTaskDone()) {
				return "redirect:/task.list/done/" + humanId;
			} else {
				return "redirect:/task.list/" + humanId;
			}
		} catch (NumberFormatException nfe) {
			model.addAttribute("error", "Ошибка переадресации");
			return "error_page";
		}	}

	@RequestMapping(value = "/task.list/{humanId}")
	public String listTask(@PathVariable("humanId") Long humanId, Model model) {// undone tasks list
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human executor;
		if (humanId.longValue() == 0L) {
			// мои задачи
			executor = managerLogin.getHuman();
			model.addAttribute("showMyTasks", true);
		} else {
			// задачи другого человека
			executor = humanService.getHuman(humanId, managerLogin);
		}
		if (executor == null) {
			return "redirect:/logout";
		}

		List<Task> list = taskService.listUndoneTask(executor, managerLogin);

		model.addAttribute("executor", executor);
		model.addAttribute("listTask", list);
		model.addAttribute("executed", false);
		return "list_task";
	}

	@RequestMapping(value = "/task.list/archive/{humanId}")
	public String listArchiveTasks(@PathVariable("humanId") Long humanId, Model model) {// undone tasks list
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human executor;
		if (humanId.longValue() == 0L) {
			// мои задачи
			executor = managerLogin.getHuman();
			model.addAttribute("showMyTasks", true);
		} else {
			// задачи другого человека
			executor = humanService.getHuman(humanId, managerLogin);
		}
		if (executor == null) {
			return "redirect:/logout";
		}

		List<Task> list = taskService.listArchiveTasks(executor, managerLogin);

		model.addAttribute("executor", executor);
		model.addAttribute("listTask", list);
		return "list_task_archive";
	}

	@RequestMapping(value = "/task.list/archive/slave")
	public String listTaskArchiveSlave(Model model) {// undone tasks list for employees
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		List<Task> list = taskService.listArchiveTasksForSlave(managerLogin);

		model.addAttribute("listTask", list);
		return "list_task_archive_slave";
	}


	@RequestMapping(value = "/task.list/slave")
	public String listTaskSlave(Model model) {// undone tasks list for employees
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		List<Task> list = taskService.listUndoneTasksForSlave(managerLogin);

		model.addAttribute("listTask", list);
		model.addAttribute("executed", false);
		return "list_task_slave";
	}

	@RequestMapping(value = "/task.list/slave/done")
	public String listDoneTaskSlave(Model model) {// done tasks list for employees
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		List<Task> list = taskService.listDoneTasksForSlave(managerLogin);

		model.addAttribute("listTask", list);
		model.addAttribute("executed", true);
		return "list_task_slave";
	}

	@RequestMapping(value = "/task.list/done/{humanId}")
	public String listDoneTask(@PathVariable("humanId") Long humanId, Model model) {// done tasks list
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Human executor;
		if (humanId.longValue() == 0L) {
			// мои задачи
			executor = managerLogin.getHuman();
			model.addAttribute("showMyTasks", true);
		} else {
			// задачи другого человека
			executor = humanService.getHuman(humanId, managerLogin);
			model.addAttribute("showMyTasks", false);
		}
		if (executor == null) {
			return "redirect:/logout";
		}

		List<Task> list = taskService.listDoneTask(executor, managerLogin);

		model.addAttribute("executor", executor);
		model.addAttribute("listTask", list);
		model.addAttribute("executed", true);
		return "list_task";
	}

	@RequestMapping("/task.delete/{taskId}/{redirect}")
	public String deleteTask(@PathVariable("taskId") Long taskId, @PathVariable("redirect") String redirect, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Task task = taskService.getTask(taskId, managerLogin);
		if (task != null) {
			taskService.removeTask(taskId, managerLogin);
		} else {
			model.addAttribute("error", "Задача не существует");
			return "error_page";
		}

		if ("archive".equals(redirect)) { // must return to archive page
			if (task.getOwner().equals(managerLogin.getHuman())) { // manager's own task, return to My tasks archive page 
				return "redirect:/task.list/archive/0";
			} else {
				return "redirect:/task.list/archive/slave";
			}
		}

		if ("slave".equals(redirect)) { // must return to slave page
			return "redirect:/task.list/slave";
		}

		try {
			Long humanId = Long.parseLong(redirect);
			if (task.getTaskDone()) {
				return "redirect:/task.list/done/" + humanId;
			} else {
				return "redirect:/task.list/" + humanId;
			}
		} catch (NumberFormatException nfe) {
			model.addAttribute("error", "Ошибка переадресации");
			return "error_page";
		}
	}

	@RequestMapping(value="/task.undone/{taskId}/{redirect}", method=RequestMethod.GET)
	public String setUndone(@PathVariable("taskId") Long taskId, @PathVariable("redirect") String redirect, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Task task = taskService.getTask(taskId, managerLogin);
		task.setDone(null);
		taskService.updateTask(task, managerLogin);

		if ("slave".equals(redirect)) {
			return "redirect:/task.list/slave/done";
		} 

		try {
			Long humanId = Long.parseLong(redirect);
			return "redirect:/task.list/done/" + humanId;
		} catch (NumberFormatException nfe) {
			model.addAttribute("error", "Ошибка переадресации");
			return "error_page";
		}
	}

	@RequestMapping(value="/task.execute/{taskId}/{redirect}", method=RequestMethod.GET)
	public String executeTask(@PathVariable("taskId") Long taskId, @PathVariable("redirect") String redirect, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Task task = taskService.getTask(taskId, managerLogin);
		task.setDone(new Date());
		taskService.updateTask(task, managerLogin);

		model.addAttribute("task", task);
		model.addAttribute("redirect", redirect);
		return "add_task_comment";
	}

	@RequestMapping(value="/task.execute/{redirect}", method=RequestMethod.POST)
	public String saveComment(@PathVariable("redirect") String redirect, @ModelAttribute("task") Task task, BindingResult result, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Task existingTask = taskService.getTask(task.getId(), managerLogin);
		if (existingTask != null) {
			existingTask.setComment(task.getComment());
			taskService.updateTask(existingTask, managerLogin);
		} else {
			return "redirect:/logout";
		}

		if ("slave".equals(redirect)) {
			return "redirect:/task.list/slave";
		} 

		try {
			Long humanId = Long.parseLong(redirect);
			return "redirect:/task.list/" + humanId;
		} catch (NumberFormatException nfe) {
			model.addAttribute("error", "Ошибка переадресации");
			return "error_page";
		}
	}

	@RequestMapping(value="/task.archive/{taskId}/{setArchive}/{redirect}", method=RequestMethod.GET)
	public String archiveTask(@PathVariable("taskId") Long taskId, 
			@PathVariable("setArchive") Long setArchive, 
			@PathVariable("redirect") String redirect, 
			Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Task task = taskService.getTask(taskId, managerLogin);
		if (task != null) {
			if (setArchive.longValue() != 0L) {
				if (task.getTaskDone()) {
					task.setArchive(true);
					taskService.updateTask(task, managerLogin);
				}
			} else {
				task.setArchive(false);
				taskService.updateTask(task, managerLogin);
			}
		}

		if ("slave".equals(redirect)) {
			if (setArchive.longValue() == 0L) {// moved from archive -> show archive page
				return "redirect:/task.list/archive/slave";
			} else { // moved to archive -> show slave tasks page
				return "redirect:/task.list/slave";
			}
		} else
			if ("slave-done".equals(redirect)) {
				return "redirect:/task.list/slave/done";
			}


		try {
			Long humanId = Long.parseLong(redirect);
			if (setArchive == 0L) {// moved from archive -> show archive page
				return "redirect:/task.list/archive/" + (humanId.equals(managerLogin.getHuman().getId()) ? "0" : humanId);
			} else { // moved to archive -> show human's tasks page
				return "redirect:/task.list/done/" + (humanId.equals(managerLogin.getHuman().getId()) ? "0" : humanId);
			}

		} catch (NumberFormatException nfe) {
		}

		model.addAttribute("error", "Ошибка переадресации");
		return "error_page";
	}

	@RequestMapping("/task.edit/{taskId}/{redirect}")
	public String editTask(@PathVariable("taskId") Long taskId, @PathVariable("redirect") String redirect, Model model) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			model.addAttribute("error", ble.getMessage());
			return "error_page";
		}

		Task editedTask = taskService.getTask(taskId, managerLogin);
		if (editedTask == null) {
			return "redirect:/logout";
		}

		model.addAttribute("executor", editedTask.getOwner());
		model.addAttribute("taskProxy", new TaskDto(editedTask));
		model.addAttribute("redirect", redirect);
		return "edit_task_form";
	}


	// AJAX

	@RequestMapping("/task.delete.ajax/{taskId}")
	public @ResponseBody AjaxResponse ajaxDeleteTask(@PathVariable("taskId") Long taskId) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			return new AjaxResponse(AjaxResponseCodes.ERROR, ble.getMessage());
		}

		Task task = taskService.getTask(taskId, managerLogin);
		if (task != null) {
			taskService.removeTask(taskId, managerLogin);
		} else {
			return new AjaxResponse(AjaxResponseCodes.ERROR,  "Задача не существует");
		}

		return new AjaxResponse(AjaxResponseCodes.SUCCESS,  "Задача удалена");
	}

	@RequestMapping(value="/task.edit.ajax/set", method=RequestMethod.POST, produces={"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody AjaxResponse ajaxEditTask(@RequestBody AjaxEditRequest request) {
		System.out.println(request.getFieldName());
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			return new AjaxResponse(AjaxResponseCodes.ERROR, ble.getMessage());
		}

		if (!managerLogin.isConfirmed()) {
			return new AjaxResponse(AjaxResponseCodes.ERROR, "Логин не подтвержден");
		}

		Task task = taskService.getTask(request.getId(), managerLogin);
		if (task != null) {
			switch(request.getFieldName()) {
			case "content":
				taskService.setTaskContent(task, request.getValue(), managerLogin);
				break;
			case "comment":
				taskService.setTaskComment(task, request.getValue(), managerLogin);
				break;
			}
		} else {
			return new AjaxResponse(AjaxResponseCodes.ERROR,  "Задача не существует");
		}

		return new AjaxResponse(AjaxResponseCodes.SUCCESS,  "Задача изменена");
	}
	
	// RESTful methods

	// for testing only !!!
	@RequestMapping("/json-test")
	public String editTask(Model model) {
		return "json-test";
	}
	
	@RequestMapping(value = "/task.synch", method=RequestMethod.POST, produces={"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody SynchContainer<TaskJsonProxy> synchronizeHumans(@RequestBody SynchContainer<TaskJsonProxy> clientRequest) {
		Login managerLogin = loginChecker.getRegisteredLogin();
		try {
			loginChecker.checkLogin(managerLogin);
		} catch (BadLoginException ble) {
			SynchContainer<TaskJsonProxy> errorContainer = new SynchContainer<TaskJsonProxy>();
			switch (ble.getReason()) {
			case BadLoginException.ACCESS_DENIED: errorContainer.setSynchStatus(SynchStatus.ERROR_ACCESS_DENIED); break;
			case BadLoginException.LOGIN_EXPIRED: errorContainer.setSynchStatus(SynchStatus.ERROR_LOGIN_EXPIRED); break;
			case BadLoginException.LOGIN_LOCKED: errorContainer.setSynchStatus(SynchStatus.ERROR_LOGIN_LOCKED); break;
			case BadLoginException.LOGIN_NOT_CONFIRMED: errorContainer.setSynchStatus(SynchStatus.ERROR_LOGIN_NOT_CONFIRMED); break;
			}
			return errorContainer;
		}

		SynchContainer<TaskJsonProxy> resp = taskService.synchronize(clientRequest, managerLogin); 
		return resp;
	}
}
