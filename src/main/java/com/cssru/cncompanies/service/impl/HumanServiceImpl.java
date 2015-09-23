package com.cssru.cncompanies.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssru.cncompanies.dao.HumanDAO;
import com.cssru.cncompanies.domain.Company;
import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.domain.Task;
import com.cssru.cncompanies.domain.Unit;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.LoginService;
import com.cssru.cncompanies.service.TaskService;
import com.cssru.cncompanies.service.UnitService;
import com.cssru.cncompanies.synch.ItemDescriptor;
import com.cssru.cncompanies.synch.SynchContainer;

@Service
public class HumanServiceImpl implements HumanService {

	@Autowired
	private HumanDAO humanDAO;

	@Autowired
	private TaskService taskService;

	@Autowired
	private UnitService unitService;

	@Autowired
	private LoginService loginService;

	@Transactional
	@Override
	public void addHuman(Human human) {
		humanDAO.addHuman(human);
	}

	@Transactional (readOnly = true)
	@Override
	public List<Human> listHuman(Unit unit, Login managerLogin) throws AccessDeniedException {
		if ( !(unit.getOwner().equals(managerLogin.getHuman()) ||
				unit.getCompany().getOwner().equals(managerLogin.getHuman())) ) {
			throw new AccessDeniedException();
		}
		return humanDAO.listHuman(unit);
	}

	@Transactional (readOnly = true)
	@Override
	public List<Human> listHuman(Company company, Login managerLogin) throws AccessDeniedException {
		if ( !company.getOwner().equals(managerLogin.getHuman()) ) {
			throw new AccessDeniedException();
		}
		return humanDAO.listHuman(company);
	}

	@Transactional
	@Override
	public void removeHuman(Long id, Login managerLogin) throws AccessDeniedException {
		Human human = getHuman(id, managerLogin);
		if (managerLogin.getHuman().equals(human)) {
			throw new AccessDeniedException();
		}
		
		// delete all tasks linked to the human
		List<Task> tasksForHuman = taskService.listAllTask(human, managerLogin);
		for (Task t:tasksForHuman)
			taskService.removeTask(t.getId(), managerLogin);
		List<Task> tasksWithAuthor = taskService.listTaskWithAuthor(human, managerLogin);
		for (Task t:tasksWithAuthor) {
			// delete human's own tasks for himself
			if (t.getAuthor().equals(human)) {
				taskService.removeTask(t.getId(), managerLogin);
			} else {
				// else set manager as task author (do not delete tasks for another users)
				t.setAuthor(managerLogin.getHuman());
				taskService.updateTask(t, managerLogin);
			}
		}

		List<Unit> units = unitService.listUnitsWithOwner(human, managerLogin);
		for (Unit u:units) {
			u.setOwner(null);
			unitService.updateUnit(u, managerLogin);
		}

		humanDAO.removeHuman(human);
	}

	@Transactional
	@Override
	public void updateHuman(Human human, Login managerLogin) throws AccessDeniedException {
		Human existingHuman = humanDAO.getHuman(human.getId());
		if (existingHuman == null ||
				!(existingHuman.getUnit().getOwner().equals(managerLogin.getHuman()) ||
						existingHuman.getUnit().getCompany().getOwner().equals(managerLogin.getHuman()))
				) {
			throw new AccessDeniedException();
		}

		humanDAO.updateHuman(human);
	}

	@Transactional (readOnly = true)
	@Override
	public Human getHuman(Long id, Login managerLogin) throws AccessDeniedException {
		Human human = humanDAO.getHuman(id);
		if (human == null ||
				!(human.getId().equals(id) ||
						human.getUnit().getOwner().equals(managerLogin.getHuman()) ||
						human.getUnit().getCompany().getOwner().equals(managerLogin.getHuman()))
				) {
			throw new AccessDeniedException();
		}
		
		return human;
	}

	// Synchronization
	@Transactional
	@Override
	public SynchContainer<Human> synchronize(SynchContainer<Human> request, Login managerLogin) throws AccessDeniedException {
		SynchContainer<Human> response = new SynchContainer<Human>();
		//TODO do synch!!!

		// tell to client, that humans are deleted on server side (when human has serverId on client's device and not found in server's database)
		for (ItemDescriptor nextItem : request.getItems()) {
			if (nextItem.getServerId() > 0) {
				if (getHuman(nextItem.getServerId(), managerLogin) == null) {
					response.addDeletedItem(nextItem);
				}
			}
		}

		// check Server's objects and send to client objects, when they modified after client's lastSynchTime
		List<Human> serverNewHumans = humanDAO.listHuman(request.getLastSynchTime(), managerLogin.getHuman());
		for (Human nextHuman : serverNewHumans) {
			response.addObject(nextHuman);
		}

		// set last synchronization time in response and in manager's (user's) login
		long lastSynchTime = System.currentTimeMillis();
		response.setLastSynchTime(lastSynchTime);
		managerLogin.setLastSynch(new Date(lastSynchTime));
		loginService.updateLogin(managerLogin, false);
		
		
		return response;
	}
}
