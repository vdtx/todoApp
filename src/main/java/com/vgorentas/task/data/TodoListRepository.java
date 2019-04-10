package com.vgorentas.task.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgorentas.task.model.TodoList;

public interface TodoListRepository  extends JpaRepository<TodoList, Integer>{

	@Query(value = "select * from todo_list where user_id = ?1 ", nativeQuery = true)
	public List<TodoList> getTodoListByUser(int userId);
	
	@Query(value = "update todo_item set status = 'completed' where todo_list_id = ?1 and id = ?2", nativeQuery = true)
	public List<TodoList> markTodoAsComplete(int todoListId, int todoItemId);
	
	@Query(value = "select dependent_to from todo_item where todo_list_id = ?1 and id = ?2", nativeQuery = true)
	public int findTodoDependent(int todoListId, int todoItemId);
	
	@Query(value = "select status from todo_item where todo_list_id = ?1 and id = ?2", nativeQuery = true)
	public String isDependentTodoCompleted(int todoListId, int dependentBy);
}
