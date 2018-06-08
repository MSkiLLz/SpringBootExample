package springboot.example.repositories;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;



import springboot.example.models.TodoList;

@Transactional
public interface TodoListDao extends CrudRepository<TodoList, Long> {

	List<TodoList> findAllByTasksList_Id(Iterable<Long> tasksIds);
}

