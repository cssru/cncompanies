package com.cssru.cncompanies.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 4096)
    private String content;

    @Column(length = 4096)
    private String comment;

    @Column(nullable = false)
    private Date created;

    @Column
    private Date begin;

    @Column(nullable = false)
    private Date expires;

    @Column
    private Date done;

    @ManyToOne
    @JoinColumn
    private Project project;

    @Column
    private Task parent;

    @OneToOne
    @JoinColumn(nullable = false)
    private Post creator;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Post executor;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(name = "alert_time", nullable = false)
    private Long alertTime;

    @Column(nullable = false)
    private Boolean archive;

    @Column(nullable = false)
    private Long version;

    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<TaskMetadataElement> metadata;

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Task)) return false;
        Task task2 = (Task) o;
        return id.equals(task2.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
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
