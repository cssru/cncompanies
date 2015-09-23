package com.cssru.cncompanies.proxy;

import com.cssru.cncompanies.domain.Human;
import com.cssru.cncompanies.domain.Login;
import com.cssru.cncompanies.service.TaskService;

public class HumanWrapper {
	private Human human;
	private Long normalTasksCount;
	private Long expiredTasksCount;
	private Long nearestTasksCount;
	private Long doneTasksCount;
	private Long archiveTasksCount;

	public HumanWrapper(Human human, TaskService taskService, Login managerLogin) {
		this.human = human;
		this.normalTasksCount = taskService.getNormalTaskCount(human, managerLogin);
		this.expiredTasksCount = taskService.getExpiredTaskCount(human, managerLogin);
		this.nearestTasksCount = taskService.getNearestTaskCount(human, managerLogin);
		this.doneTasksCount = taskService.getDoneTaskCount(human, managerLogin);
		this.archiveTasksCount = taskService.getArchiveTaskCount(human, managerLogin);
	}


	public Human getHuman() {
		return human;
	}

	public Long getNormalTasksCount() {
		return normalTasksCount;
	}

	public Long getExpiredTasksCount() {
		return expiredTasksCount;
	}

	public Long getNearestTasksCount() {
		return nearestTasksCount;
	}

	public Long getDoneTasksCount() {
		return doneTasksCount;
	}

	public Long getArchiveTasksCount() {
		return archiveTasksCount;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public void setNormalTasksCount(Long normalTasksCount) {
		this.normalTasksCount = normalTasksCount;
	}

	public void setExpiredTasksCount(Long expiredTasksCount) {
		this.expiredTasksCount = expiredTasksCount;
	}

	public void setNearestTasksCount(Long nearestTasksCount) {
		this.nearestTasksCount = nearestTasksCount;
	}

	public void setDoneTasksCount(Long doneTasksCount) {
		this.doneTasksCount = doneTasksCount;
	}

	public void setArchiveTasksCount(Long archiveTasksCount) {
		this.archiveTasksCount = archiveTasksCount;
	}

}
