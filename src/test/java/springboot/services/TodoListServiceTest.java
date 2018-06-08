package springboot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import springboot.example.models.Task;
import springboot.example.models.TodoList;
import springboot.example.models.User;
import springboot.example.repositories.TaskDao;
import springboot.example.repositories.TodoListDao;
import springboot.example.services.TodoListService;

public class TodoListServiceTest
{
	@Mock
	private TodoListDao todoListDao;

	@Mock
	private TaskDao taskDao;

	@InjectMocks
	private TodoListService todoListService = new TodoListService();
	
	private TodoList testTodoList;
	private String testTodoListName = "Todo List 1";
	private long testTodoListId = 1l;
	
	private Task testTask;
	private long testTaskId = 1l;
	private String testTaskName = "Task-1";
	private String testTaskDescription = "Task-1 Description";
	List<Task> testTasksList;
	
	private User testUser;
	private long testUserId = 1l;
	private String testUserName = "testUserName1";

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		
		testUser = new User(testUserName);
		testUser.setId(testUserId);
		
		testTask = new Task();
		testTask.setId(testTaskId);
		testTask.setName(testTaskName);
		testTask.setDescription(testTaskDescription);
		testTask.setAssignedUser(testUser);
		
		testTodoList = new TodoList();
		testTodoList.setId(testTodoListId);
		testTodoList.setName(testTodoListName);
		testTodoList.addTask(testTask);
		
		testTasksList = new ArrayList<Task>();
		testTasksList.add(testTask);
	}
	
	@Test
	public void createTodoListTest() throws Exception {
		Mockito.when(todoListDao.save(Mockito.any(TodoList.class))).thenReturn(testTodoList);

		TodoList todoList = todoListService.saveTodoList(testTodoList);

		Mockito.verify(todoListDao, Mockito.times(1)).save(Mockito.any(TodoList.class));
		assertNotNull(todoList);
		assertNotNull(todoList.getId());
		assertEquals(testTodoListId, todoList.getId());
	}
	
	@Test
	public void addTaskToTodoListTest() throws Exception {
		
		List<Long> taskListIds = Arrays.asList(testTaskId);
		Mockito.when(todoListDao.findOne(testTodoListId)).thenReturn(testTodoList);
		Mockito.when(taskDao.findAll(taskListIds)).thenReturn(testTasksList);
		Mockito.when(todoListDao.save(Mockito.any(TodoList.class))).thenReturn(testTodoList);

		TodoList todoList = todoListService.addTasks(testTodoListId, taskListIds);

		Mockito.verify(todoListDao, Mockito.times(1)).save(Mockito.any(TodoList.class));
		assertNotNull(todoList);
		assertNotNull(todoList.getId());
		assertNotNull(todoList.getTasksList());
		assertEquals(testTaskId, todoList.getTasksList().iterator().next().getId());
	}
	
	@Test
	public void findTodoListByIdTest() throws Exception {
		
		Mockito.when(todoListDao.findOne(testTodoListId)).thenReturn(testTodoList);

		TodoList todoList = todoListService.findTodoListById(testTodoListId);

		Mockito.verify(todoListDao, Mockito.atLeastOnce()).findOne(testTodoListId);
		assertNotNull(todoList);
		assertEquals(testTodoListId, todoList.getId());
	}
	
	@Test
	public void listAllTasksGroupedByTodoListTest() throws Exception {
		
		List<TodoList> todosList = Arrays.asList(testTodoList);
		
		Mockito.when(todoListDao.findAll()).thenReturn(todosList);

		Iterable<TodoList> todoListResult = todoListService.listAllTasksGroupedByTodoList();

		Mockito.verify(todoListDao, Mockito.atLeastOnce()).findAll();
		assertNotNull(todoListResult);
		assertEquals(testTodoListId, todoListResult.iterator().next().getId());
	}
	
	@Test
	public void deleteTodoListTest() {

		todoListService.deleteTodoListById(testTodoListId);

        Mockito.verify(todoListDao, Mockito.times(1)).delete(testTodoListId);        
	}
	
	@Test
	public void deleteTasksInTodoListTest() throws Exception {
		List<Long> taskIds = Arrays.asList(testTaskId);
		Mockito.when(todoListDao.findOne(testTodoListId)).thenReturn(testTodoList);
		Mockito.when(todoListDao.save(testTodoList)).thenReturn(testTodoList);
		Mockito.when(taskDao.findAll(taskIds)).thenReturn(testTasksList);
		
		TodoList todoList = todoListService.deleteTasksInTodoList(testTodoListId, taskIds);  
		
		Mockito.verify(todoListDao, Mockito.times(1)).save(Mockito.any(TodoList.class));
		assertNotNull(todoList);
		assertEquals(testTodoListId, todoList.getId());
	}
}
