package springboot.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import springboot.example.models.Task;
import springboot.example.models.TaskStatus;
import springboot.example.models.User;
import springboot.example.repositories.TaskDao;
import springboot.example.repositories.UserDao;
import springboot.example.services.TaskService;

public class TaskServiceTest
{
	@Mock
	private TaskDao taskDao;

	@Mock
	private UserDao userDao;

	@InjectMocks
	private TaskService taskService = new TaskService();
	
	private Task testTask;
	private long testTaskId = 1l;
	private String testTaskName = "Task-1";
	private String testTaskDescription = "Task-1 Description";
	private TaskStatus testTaskStatus = TaskStatus.notstarted;
	List<Task> tasks;
	
	private User testUser;
	private long testUserId = 1l;
	private String testUserName = "testUserName1";
	

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks(this);
		testTask = new Task();
		testTask.setId(testTaskId);
		testTask.setName(testTaskName);
		testTask.setDescription(testTaskDescription);
		testTask.setStatus(testTaskStatus);
		
		testUser = new User(testUserName);
		testUser.setId(testUserId);
		
		testTask.setAssignedUser(testUser);
		
		tasks = new ArrayList<Task>();
		tasks.add(testTask);
	}
	
	@Test
	public void createTaskTest() throws Exception {
		Mockito.when(userDao.findOne(Mockito.anyLong())).thenReturn(testUser);
		Mockito.when(taskDao.save(Mockito.any(Task.class))).thenReturn(testTask);

		Task savedTask = taskService.CreateOrUpdateTask(testUserId, testTask);

		Mockito.verify(taskDao, Mockito.times(1)).save(Mockito.any(Task.class));
		assertNotNull(savedTask);
		assertNotNull(savedTask.getId());
		assertEquals(testTaskName, savedTask.getName());
	}
	
	@Test
	public void updateTaskTest() throws Exception {

		String updatedTaskName = "Task-1 Updated";
		
		Task updatedTask = new Task( );
		updatedTask.setId(testTaskId);
		updatedTask.setName(updatedTaskName);
		updatedTask.setDescription(testTaskDescription);
		updatedTask.setStatus(testTaskStatus);
		updatedTask.setAssignedUser(testUser);

		Mockito.when(userDao.findOne(Mockito.anyLong())).thenReturn(testUser);
		Mockito.when(taskDao.save(updatedTask)).thenReturn(updatedTask);

		Task taskResult = taskService.CreateOrUpdateTask(testUserId, updatedTask);

		Mockito.verify(userDao, Mockito.times(1)).findOne(testUserId);
		Mockito.verify(taskDao, Mockito.times(1)).save(updatedTask);

		assertNotNull(taskResult);
		assertEquals(testTaskId, taskResult.getId());
		assertEquals(updatedTaskName, taskResult.getName());
	}
	
	@Test
	public void findTaskByStatusTest() throws Exception {
		Mockito.when(taskDao.findByStatus(TaskStatus.notstarted)).thenReturn(tasks);

		List<Task> tasksByStatus = taskService.findByStatus(testTaskStatus.toString());

		Mockito.verify(taskDao, Mockito.atLeastOnce()).findByStatus(TaskStatus.notstarted);
		assertNotNull(tasksByStatus);
		assertEquals(testTaskStatus, tasksByStatus.get(0).getStatus());
	}
	
	@Test
	public void findTaskByUserIdTest() throws Exception {
		Mockito.when(taskDao.findByAssignedUserId(testUserId)).thenReturn(tasks);

		List<Task> userTasks = taskService.findByAssignedUserId(testUserId);

		Mockito.verify(taskDao, Mockito.atLeastOnce()).findByAssignedUserId(testUserId);
		assertNotNull(userTasks);
		assertEquals(testUser.getId(), userTasks.get(0).getAssignedUser().getId());
	}
	
	@Test
	public void findNotCompletedTasksTest() throws Exception {
		Mockito.when(taskDao.findByStatusNot(TaskStatus.complete)).thenReturn(tasks);

		List<Task> tasks = taskService.findByStatusNot(TaskStatus.complete);

		Mockito.verify(taskDao, Mockito.atLeastOnce()).findByStatusNot(TaskStatus.complete);
		assertNotNull(tasks);
		assertEquals(testTaskStatus, tasks.get(0).getStatus());
	}
}
