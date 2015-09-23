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

@JsonAutoDetect(fieldVisibility = Visibility.NONE, getterVisibility = Visibility.ANY, setterVisibility = Visibility.NONE)
@Entity
@Table (name="tasks")
public class Task implements Serializable {
	@Transient
	private static final long serialVersionUID = 1L;
	@Transient
	public static final int ARCHIVE = 1;
	@Transient
	public static final int NON_ARCHIVE = 0;

	@Id
	@Column
	@GeneratedValue
	private Long id;
	
	@Transient
	private Long deviceClientId;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JoinColumn (name="owner_id")
	private Human owner;

	@ManyToOne
	@JoinColumn (name="author_id")
	private Human author;

	@Column
	private Date created;
	
	@Column
	private Date begin;
	
	@Column
	private Date expires;

	@Column
	private Date done;
	
	@Column
	private String content;

	@Column
	private String comment;

	@Column
	private Integer difficulty;
	
	@Column (name="parent_task")
	private Long parentTaskId;
	
	@Column (name="alert_time")
	private Long alertTime;
	
	@Column (name="is_archive", nullable=false, columnDefinition="TINYINT(1)")
	private Boolean archive;
	
	@Column (name="project_id")
	private Long projectId;
	
	@Column (name="last_modified")
	private Date lastModified;
	
	@OneToMany (mappedBy = "taskId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<TaskMetadataElement> metadata;
	
	public Task() {
		id = 0L;
		owner = null;
		created = new Date();
		begin = null;
		expires = null;
		done = null;
		content = "";
		comment = "";
		difficulty = 0;
		parentTaskId = -1L;
		alertTime = 0L;
		archive = false;
		projectId = -1L;
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

	public Long getClientId() {
		return deviceClientId;
	}

	@JsonIgnore
	public Human getOwner() {
		return owner;
	}
	
	public Long getOwnerId() {
		return owner.getId();
	}
	
	@JsonIgnore
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

	@JsonIgnore
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

	public void setServerId(Long serverId) {
		this.id = serverId;
	}

	public void setClientId(Long clientId) {
		this.deviceClientId = clientId;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
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
	
	public void setParentTaskId(Long parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	
	public void setAlertTime(Long alertTime) {
		this.alertTime = alertTime.longValue() < 0L ? 0L : alertTime;
	}
	
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
