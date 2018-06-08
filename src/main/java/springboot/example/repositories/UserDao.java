package springboot.example.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import springboot.example.models.User;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {

  User findByUserName(String userName);

}