package com.vgorentas.task.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vgorentas.task.data.TodoItemRepository;
import com.vgorentas.task.data.TodoListRepository;
import com.vgorentas.task.model.TodoItem;
import com.vgorentas.task.model.TodoList;

@RestController
public class TodoListController {

	@Autowired
	private TodoListRepository todoListRepository;
	
	@Autowired
	private TodoItemRepository todoItemRepository;
	
	@PostMapping(value = "/addtodolist")
	public String createTodoList(@RequestBody TodoList todoList) {
		try {
			todoListRepository.save(todoList);
			return todoList.getListName() + " list saved";
		}catch(Exception ex) {
			return "failed";
		}
	}
	
	@GetMapping(value = "/gettodolists")
	public List<TodoList> getTodoLists(){
		return todoListRepository.findAll();
	}
	
	@GetMapping(value = "/gettodolistbyuser/{userid}")
	public List<TodoList> getTodoListByUser(@PathVariable("userid") int userId){
		return todoListRepository.getTodoListByUser(userId);
	}
	
	@GetMapping(value = "/todolist/{todoListId}")
	public Optional<TodoList> getTodoList(@PathVariable("todoListId") int id){
		return todoListRepository.findById(id);
	}
	
	@PostMapping(value = "/additem")
	public String addTodoItem(@RequestBody TodoItem todoItem) {
		try {
			todoItemRepository.save(todoItem);
			return todoItem.getName() + " item saved";
		}catch(Exception ex) {
			return "failed";
		}
	}
	
	@DeleteMapping(value = "/deleteitem/{todoId}")
	public String deleteTodoItem(@PathVariable("todoId") int id) {
		final Optional<TodoItem> todoItem = todoItemRepository.findById(id);
		if(!StringUtils.isEmpty(todoItem)) {
			todoItemRepository.deleteById(id);
			return todoItem.get().getName() + " item deleted";
		}
		return "failed to delete";
	}
	
	@GetMapping(value = "/order/{id}/{by}")
	public List<TodoItem> orderTodoListBy(@PathVariable("by") String by, @PathVariable("id") int id){
		String orderBy = "";
		switch(by) {
		case "date":
			orderBy = "date";
			break;
		case "name":
			orderBy = "name";
			break;
		case "status":
			orderBy = "status";
			break;
		case "deadline":
			orderBy = "deadline";
			break;
		}
		return !StringUtils.isEmpty(orderBy) ? orderTodoList(id,orderBy) : null;
	}
	
	public List<TodoItem> orderTodoList(int id, String orderBy){
		return todoItemRepository.getTodoItems(id, orderBy);
	}
	
	@PutMapping(value = "/markascomplete/{todoListId}/{todoItemId}")
	public String markTodoCompleted(@PathVariable("todoListId") int todoListId, @PathVariable("todoItemId") int todoItemId) {
		final int dependentBy = isTodoDependent(todoListId, todoItemId);
		if(dependentBy <= 0) {
			todoListRepository.markTodoAsComplete(todoListId, todoItemId);
				return "completed";
		}else {
			final String temp = todoListRepository.isDependentTodoCompleted(todoListId, dependentBy);
				if("completed".equals(temp)) {
					todoListRepository.markTodoAsComplete(todoListId, todoItemId);
					return "completed";
				}else {
					return "complete todo #" + dependentBy; 
				}
			
		}
	}
	
	private int isTodoDependent(int todoListId, int todoItemId) {
		int dependentTodo = todoListRepository.findTodoDependent(todoListId, todoItemId);
		return dependentTodo > 0 ? dependentTodo: -1;
	}
}
