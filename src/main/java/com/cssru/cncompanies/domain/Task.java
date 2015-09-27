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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name="tasks")
public class Task {
	@Id
	@Column
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn (nullable = false)
	private Human executor;

	@ManyToOne
	@JoinColumn (nullable = false)
	private Human author;

	@Column (nullable = false)
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

	@Column (nullable = false)
	private Integer difficulty;
	
	@Column
	private Task parent;
	
	@Column (name="alert_time")
	private Long alertTime;
	
	@Column (nullable = false)
	private Boolean archive;
	
	@Column
	private Project project;
	
	@Column (nullable = false)
	private Long version;
	
	@OneToMany (mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TaskMetadataElement> metadata;
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Task)) return false;
		Task task2 = (Task)o;
		return id.equals(task2.getId());
	}

}
