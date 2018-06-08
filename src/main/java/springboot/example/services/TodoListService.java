package springboot.example.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.example.exceptions.ResourceNotFoundException;
import springboot.example.models.Task;
import springboot.example.models.TodoList;
import springboot.example.repositories.TaskDao;
import springboot.example.repositories.TodoListDao;

@Service
public class TodoListService
{
	@Autowired
	private TodoListDao todoListDao;
	
	@Autowired
	private TaskDao taskDao;
	
	@Transactional
	public TodoList addTasks(long todoListId, Iterable<Long> taskIds) throws Exception{
		
		TodoList todoList = this.findTodoListById(todoListId);
		if(todoList == null){
			throw new ResourceNotFoundException ("Todolist Id not found: " + todoListId);
		}
		
		Iterable<Task> tasks = taskDao.findAll(taskIds);
		if(tasks == null){
			throw new ResourceNotFoundException ("Tasks not found");
		}
		
		tasks.forEach(task-> todoList.addTask(task));
		
		return this.saveTodoList(todoList);	
	}

	@Transactional
	public Iterable<TodoList> listAllTasksGroupedByTodoList()
	{
		return todoListDao.findAll();
	}

	@Transactional
	public TodoList findTodoListById(Long todoListId)
	{
		return todoListDao.findOne(todoListId);
	}
	
	@Transactional
	public TodoList saveTodoList(TodoList todoList)
	{
		return todoListDao.save(todoList);
	}
	
	@Transactional
	public TodoList deleteTasksInTodoList(long todoListId, Iterable<Long> tasksIds) throws Exception
	{
		TodoList todoList = this.findTodoListById(todoListId);
		if(todoList== null){
			throw new ResourceNotFoundException ("Todolist Id not found: " + todoListId);
		}
		
		Iterable<Task> tasks = taskDao.findAll(tasksIds);
		if(tasks == null){
			throw new ResourceNotFoundException ("Tasks not found");
		}
		
		tasks.forEach(task-> todoList.removeTask(task));

		return this.saveTodoList(todoList);	
	}
	
	@Transactional
	public void deleteTodoListById(Long todoListId)
	{
		todoListDao.delete(todoListId);
	}

}
