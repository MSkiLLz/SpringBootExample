package springboot.example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import springboot.example.models.Task;
import springboot.example.models.TaskStatus;

@Transactional
public interface TaskDao extends CrudRepository<Task, Long> {
  
  public List<Task> findByAssignedUserId(Long userId);
  
  public List<Task> findByStatus(TaskStatus status);
  
  public List<Task> findByStatusNot(TaskStatus notStatus);

}