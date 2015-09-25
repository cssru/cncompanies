package com.cssru.cncompanies.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.cssru.cncompanies.proxy.TaskJsonProxy;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name="tasks")
public class Task {
	@Id
	@Column
	@GeneratedValue
	private Long id;

	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn
	private Human executor;

	@ManyToOne
	@JoinColumn
	private Human author;

	@Column
	private Date created;
	
	@Column
	private Date begin;
	
	@Column
	private Date expires;

	@Column
	private Date done;
	
	@Column (length = 4096)
	private String content;

	@Column (length = 2048)
	private String comment;

	@Column
	private Integer difficulty;
	
	@Column
	private Task parent;
	
	@Column (name="alert_time")
	private Long alertTime;
	
	@Column (name = "is_archive", nullable = false, columnDefinition="TINYINT(1)")
	private Boolean archive;
	
	@Column
	private Project project;
	
	@Column (name="last_modified")
	private Date lastModified;
	
	@OneToMany (mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TaskMetadataElement> metadata;
	
	public Task() {
		id = 0L;
		created = new Date();
		content = "";
		comment = "";
		difficulty = 0;
		alertTime = 0L;
		archive = false;
		lastModified = created;
		metadata = new HashSet<TaskMetadataElement>();
	
	}
	
	//getters
	public Long getId() {
		return id;
	}

	public Long getServerId() {
		return id;
	}

	public Human getExecutor() {
		return executor;
	}
	
	public Human getAuthor() {
		return author;
	}

	public Long getAuthorId() {
		return author.getId();
	}

	public String getAuthorName() {
		return author.getShortName();
	}

	public Date getCreated() {
		return created;
	}
	
	public Date getBegin() {
		return begin;
	}
	
	public Date getExpires() {
		return expires;
	}

	public Boolean getTaskDone() {
		return done != null;
	}

	public Date getDone() {
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
	
	public Task getParentTask() {
		return parent;
	}
	
	public Long getAlertTime() {
		return alertTime;
	}
	
	public Boolean isArchive() {
		return archive;
	}
	
	public Long getProject() {
		return project;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
	public Set<TaskMetadataElement> getMetadata() {
		return metadata;
	}
	
	//setters
	public void setId(Long id) {
		this.id = id;
	}

	public void setExecutor(Human executor) {
		this.executor = executor;
	}

	public void setAuthor(Human author) {
		this.author = author;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	
	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public void setDone(Date done) {
		this.done = done;
	}

	public void setTaskDone(Boolean taskDone) {
		this.done = taskDone.booleanValue() ? new Date() : null;
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
	
	public void setAlertTime(Long alertTime) {
		this.alertTime = alertTime.longValue() < 0L ? 0L : alertTime;
	}
	
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	public void setMetadata(Set<TaskMetadataElement> metadata) {
		this.metadata = metadata;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Task)) return false;
		Task task2 = (Task)o;
		return id.equals(task2.getId());
	}

}
