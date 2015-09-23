package com.cssru.cncompanies.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="task_metadata")
public class TaskMetadataElement implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue
	private Long id;
	
	@Column (name = "task_id")
	private Long taskId;
	
	@Column (name="type")
	private Integer type;
	
	@Column (name="num_value1")
	private Long numValue1;

	@Column (name="num_value2")
	private Long numValue2;

	@Column (name="str_value")
	private String strValue;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public Integer getType() {
		return type;
	}

	public Long getNumValue1() {
		return numValue1;
	}

	public Long getNumValue2() {
		return numValue2;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTask(Long taskId) {
		this.taskId = taskId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setNumValue1(Long numValue1) {
		this.numValue1 = numValue1;
	}

	public void setNumValue2(Long numValue2) {
		this.numValue2 = numValue2;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	
}
