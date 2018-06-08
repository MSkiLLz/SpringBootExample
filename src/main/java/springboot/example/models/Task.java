package springboot.example.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tasks")
public class Task
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	  
	private String name;
	private String description;
	private TaskStatus status = TaskStatus.notstarted;
	
	@ManyToOne
	private User assignedUser;
	
	@ManyToMany(mappedBy = "tasksList")
	@JsonIgnore
	private Set<TodoList> todoList = new HashSet<>();
	
	public Task(){
		
	}
	
	public Task(String name){
		this.name = name;
	}
	
	
	public Task(String name, String description, String userName){
		this.name = name;
		this.description =  description;
		this.assignedUser = new User(userName);
	}
	
	/**
	 * @return the todoList
	 */
	public Set<TodoList> getTodoList()
	{
		return todoList;
	}

	/**
	 * @param todoList the todoList to set
	 */
	public void setTodoList(Set<TodoList> todoList)
	{
		this.todoList = todoList;
	}
	
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the status
	 */
	public TaskStatus getStatus()
	{
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(TaskStatus status)
	{
		this.status = status;
	}
			
	
	/**
	 * @return the assignedUser
	 */
	public User getAssignedUser()
	{
		return assignedUser;
	}

	/**
	 * @param assignedUser the assignedUser to set
	 */
	public void setAssignedUser(User assignedUser)
	{
		this.assignedUser = assignedUser;
	}
	
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        return !(id != 0 ? !(id == (task.id)) : task.id != 0);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result;
        return result;
    }
    
    @Override
    public String toString() {
        return String.format(
                "Task[id=%d, name='%s', status='%s', user='%s']",
                id, name, status.toString(), assignedUser.getUserName());
    }
}

