package springboot.example.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.example.exceptions.ResourceNotFoundException;
import springboot.example.models.Task;
import springboot.example.models.TaskStatus;
import springboot.example.models.User;
import springboot.example.repositories.TaskDao;
import springboot.example.repositories.UserDao;
import springboot.example.utils.EnumConverter;


@Service
public class TaskService
{
	@Autowired
    private TaskDao taskDao;
	
	@Autowired
    private UserDao userDao;

	@Transactional
    public Iterable<Task> findAll() {
    	return taskDao.findAll();
    }
	
	@Transactional
	public Task CreateOrUpdateTask(Long userId, Task task) throws Exception {
		User user = userDao.findOne(userId);
		if(user == null){
			throw new ResourceNotFoundException("UserId not found: " + userId);
		}
		task.setAssignedUser(user);
		return taskDao.save(task);
	}

    @Transactional
	public List<Task> findByStatus(String status) throws Exception {
    	TaskStatus taskStatus = EnumConverter.convert(status);
    	if(taskStatus == null){
        	throw new ResourceNotFoundException ("Invalid status");	 		
    	}
    	else{
    		return taskDao.findByStatus(taskStatus);  
    	}
	}
    
    @Transactional
  	public List<Task> findByAssignedUserId(long userId) { 
    	return  taskDao.findByAssignedUserId(userId);	
  	}
    
    @Transactional
	public List<Task> findByStatusNot(TaskStatus excludedStatus) { 
    	return taskDao.findByStatusNot(excludedStatus);	
	}	

}
