package com.vgorentas.task.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TodoItem {

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;
	
	private String name;
	private String description;
	private Date deadline;
	private Date createDate;
	private boolean status;
	private int dependentTo;
	private int todoListId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getDependentTo() {
		return dependentTo;
	}
	public void setDependentTo(int dependentTo) {
		this.dependentTo = dependentTo;
	}
	public int getTodoListId() {
		return todoListId;
	}
	public void setTodoListId(int todoListId) {
		this.todoListId = todoListId;
	}
	
}
