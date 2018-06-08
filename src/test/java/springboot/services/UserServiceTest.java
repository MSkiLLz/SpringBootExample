package springboot.services;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import springboot.example.models.*;
import springboot.example.repositories.*;
import springboot.example.services.UserService;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class UserServiceTest {

	@Mock
	private UserDao userDao;
	
	@Mock
	private TaskDao taskDao;

	@InjectMocks
	private UserService userService = new UserService();

	private long testUserId = 1l;
	private String testUserName = "testUserName1";
	private User testUser;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		testUser = new User(testUserName);
		testUser.setId(testUserId);
	}

	@Test
	public void exampleTest() {
		assertEquals(1, 1);
	}

	@Test
	public void createUserTest() {
		Mockito.when(userDao.save(Mockito.any(User.class))).thenReturn(testUser);

		User user = userService.createUser(testUserName);

		Mockito.verify(userDao, Mockito.times(1)).save(Mockito.any(User.class));
		assertNotNull(user);
		assertNotNull(user.getId());
		assertEquals(testUserName, user.getUserName());
	}

	@Test
	public void updateUserTest() throws Exception {
		String updatedUserName = "updatedUserName";
		User updatedUser = new User(updatedUserName);
		updatedUser.setId(testUserId);

		Mockito.when(userDao.findOne(testUserId)).thenReturn(testUser);
		Mockito.when(userDao.save(updatedUser)).thenReturn(updatedUser);

		User user = userService.updateUser(testUserId, updatedUserName);

		Mockito.verify(userDao, Mockito.times(1)).findOne(testUserId);
		Mockito.verify(userDao, Mockito.times(1)).save(updatedUser);

		assertNotNull(user);
		assertEquals(testUserId, user.getId());
		assertEquals(updatedUserName, user.getUserName());
	}
	
	@Test
	public void findUserByUserNameTest() {
		Mockito.when(userDao.findByUserName(testUserName)).thenReturn(testUser);

		User existingUser = userService.findByIdOrUserName(testUserName);

		Mockito.verify(userDao, Mockito.times(1)).findByUserName(testUserName);
		
		assertNotNull(existingUser);
		assertNotNull(existingUser.getId());
		assertEquals(testUserName, existingUser.getUserName());
	}
	
	@Test
	public void findUserByIdTest() {
		Mockito.when(userDao.findOne(testUserId)).thenReturn(testUser);

		User existingUser = userService.findByIdOrUserName(Long.toString(testUserId));

		Mockito.verify(userDao, Mockito.times(1)).findOne(testUserId);
		
		assertNotNull(existingUser);
		assertNotNull(existingUser.getId());
		assertEquals(testUserId, existingUser.getId());
	}
	
	
	@Test
	public void deleteUserTest() {
		Mockito.when(taskDao.findByAssignedUserId(testUserId)).thenReturn(null);

		userService.deleteUser(testUserId);

        Mockito.verify(userDao, Mockito.times(1)).delete(testUserId);        
	}
}
