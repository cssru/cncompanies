package com.cssru.cncompanies.proxy;

import com.cssru.cncompanies.domain.Task;


public class TaskProxy {
	private Long id;
	private Long expiresMillis; // UTC
	private Long alertTime;
	private boolean done;
	private String content;
	private String comment;
	private Integer difficulty;
	
	public TaskProxy() {
		id = -1L;
		expiresMillis = 0L;
		alertTime = 0L;
		done = false;
		content = "";
		comment = "";
		difficulty = 0;
	}
	
	public TaskProxy(Task task) {
		id = task.getId();
		expiresMillis = task.getExpires() == null ? 0 : task.getExpires().getTime();
		alertTime = task.getAlertTime().longValue() < 0L ? 0L : task.getAlertTime();
		done = task.getDone() != null;
		content = task.getContent();
		comment = task.getComment();
		difficulty = task.getDifficulty();
	}
	
	//getters
	public Long getId() {
		return id;
	}
	
	public Long getExpiresMillis() {
		return expiresMillis;
	}

	public Long getAlertTime() {
		return alertTime;
	}

	public boolean isDone() {
		return done;
	}
	
	public String getContent() {
		return content;
	}

	public String getComment() {
		return comment;
	}

	public Integer getDifficulty() {
		return difficulty;
	}
	
	//setters
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setExpiresMillis(Long expiresMillis) {
		this.expiresMillis = expiresMillis;
	}

	public void setAlertTime(Long alertTime) {
		this.alertTime = alertTime.longValue() < 0L ? 0L : alertTime;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public void setContent(String content) {
		this.content = content;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

}
