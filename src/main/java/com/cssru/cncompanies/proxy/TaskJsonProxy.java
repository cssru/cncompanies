package com.cssru.cncompanies.proxy;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cssru.cncompanies.domain.Task;
import com.cssru.cncompanies.domain.TaskMetadataElement;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
public class TaskJsonProxy implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean readonly; 
	private Long serverId;
	private Long clientId;
	private Long ownerId;
	private Long authorId;
	private Long created;
	private Long begin;
	private Long expires;
	private Long done;
	private String content;
	private String comment;
	private String authorName;
	private Integer difficulty;
	private Long parentTaskId;
	private Long alertTime;
	private Boolean archive;
	private Long projectId;
	private Long lastModified;
	private Set<TaskMetadataElement> metadata;
	
	public TaskJsonProxy() {
		serverId = 0L;
		ownerId = 0L;
		authorId = 0L;
		created = 0L;
		begin = 0L;
		expires = 0L;
		done = 0L;
		content = "";
		comment = "";
		authorName = "";
		difficulty = 0;
		parentTaskId = -1L;
		alertTime = 0L;
		archive = false;
		projectId = -1L;
		lastModified = 0L;
		metadata = null;
	}

	public TaskJsonProxy(Task task) {
		serverId = task.getId();
		ownerId = task.getOwnerId();
		authorId = task.getAuthorId();
		created = task.getCreated().getTime();
		begin = task.getBegin().getTime();
		expires = task.getExpires().getTime();
		done = task.getDone().getTime();
		content = task.getContent();
		comment = task.getComment();
		authorName = task.getAuthorName();
		difficulty = task.getDifficulty();
		parentTaskId = task.getParentTaskId();
		alertTime = task.getAlertTime();
		archive = task.isArchive();
		projectId = task.getProjectId();
		lastModified = task.getLastModified().getTime();
		metadata = task.getMetadata();
	}

	//getters
	public Long getServerId() {
		return serverId;
	}

	public Long getClientId() {
		return clientId;
	}

	public Long getOwnerId() {
		return ownerId;
	}
	
	public Long getAuthorId() {
		return authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public Long getCreated() {
		return created;
	}
	
	public Long getBegin() {
		return begin;
	}
	
	public Long getExpires() {
		return expires;
	}

	public Long getDone() {
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
	
	public Long getParentTaskId() {
		return parentTaskId;
	}
	
	public Long getAlertTime() {
		return alertTime;
	}
	
	public Boolean isArchive() {
		return archive;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	
	public Long getLastModified() {
		return lastModified;
	}
	
	public Set<TaskMetadataElement> getMetadata() {
		return metadata;
	}
	
	public boolean isReadonly() {
		return readonly;
	}
	
	//setters
	
	public Boolean getArchive() {
		return archive;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public void setBegin(Long begin) {
		this.begin = begin;
	}

	public void setExpires(Long expires) {
		this.expires = expires;
	}

	public void setDone(Long done) {
		this.done = done;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public void setAlertTime(Long alertTime) {
		this.alertTime = alertTime;
	}

	public void setArchive(Boolean archive) {
		this.archive = archive;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setLastModified(Long lastModified) {
		this.lastModified = lastModified;
	}

	public void setMetadata(Set<TaskMetadataElement> metadata) {
		this.metadata = metadata;
	}
}
