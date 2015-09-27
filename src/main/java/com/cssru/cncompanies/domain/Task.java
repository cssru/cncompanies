package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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
	
	@Column (name="alert_time", nullable = false)
	private Long alertTime;
	
	@Column (nullable = false)
	private Boolean archive;
	
	@ManyToOne
    @JoinColumn
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

    @PrePersist
    private void initNewEntity() {
        setArchive(false);
        setCreated(new Date());
        setDifficulty(0);
        setVersion(0L);
        setAlertTime(0L);
    }

    @PreUpdate
    private void setNextVersion() {
        setVersion(getVersion() + 1L);
    }

}
