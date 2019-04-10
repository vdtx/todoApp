package com.vgorentas.task.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vgorentas.task.model.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Integer>{

	@Query(value = "select * from todo_item where id = ?1 order by = ?2", nativeQuery = true)
	public List<TodoItem> getTodoItems(int id, String orderBy);
}
