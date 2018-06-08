package springboot.example.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.example.exceptions.ResourceNotFoundException;
import springboot.example.models.Task;
import springboot.example.models.TodoList;
import springboot.example.models.User;
import springboot.example.repositories.TaskDao;
import springboot.example.repositories.TodoListDao;
import springboot.example.repositories.UserDao;


@Service
public class UserService
{
	@Autowired
    private UserDao userDao;
	
	@Autowired
    private TaskDao taskDao;
	
	@Autowired
	private TodoListDao todoListDao;

	@Transactional
    public Iterable<User> findAll() {
    	return userDao.findAll();
    }

    @Transactional
	public User findByIdOrUserName(String idOrUserName) {
		User user;

        try {
            Long id = new Long(idOrUserName);
            user = userDao.findOne(id);
        } catch (Exception e) {
            user = null;
        }

        if (user == null) {
            user = userDao.findByUserName(idOrUserName);
        }

        return user;
	}

	@Transactional
	public User createUser(String userName) {
		User user = new User(userName);
        return userDao.save(user);
	}

	@Transactional
	public User updateUser(Long userId, String newUserName) throws Exception {

        User user = userDao.findOne(userId);
        if(user == null){
        	throw new ResourceNotFoundException ("UserId not found: " + userId);
        }
        user.setUserName(newUserName);
        return userDao.save(user);
	}
	
	@Transactional
	public void deleteUser(Long userId)
	{
		//Get all tasks from this user. We will need to remove them from any todo list.
		List<Task> userTasks = taskDao.findByAssignedUserId(userId);
		
		if(userTasks!= null && userTasks.size() > 0){

			List<Long> tasksIds = userTasks.stream().map(Task::getId).collect(Collectors.toList());
			
			List<TodoList> todoLists = todoListDao.findAllByTasksList_Id(tasksIds);
			
			//Breaks the relationship between user's tasks and todo lists
			if(todoLists!= null && todoLists.size() > 0){
				
				for(TodoList todoList : todoLists){
					for(Task task : userTasks){
						todoList.removeTask(task);
					}
				}
				
				//Save the todoLists without the user's tasks.
				todoListDao.save(todoLists);
			}
		}
		
		//When deleting a user, its tasks will be deleted.
		userDao.delete(userId);
	}

}
