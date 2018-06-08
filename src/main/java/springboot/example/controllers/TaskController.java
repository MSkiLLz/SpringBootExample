package springboot.example.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springboot.example.models.Task;
import springboot.example.models.TaskStatus;
import springboot.example.services.TaskService;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Fetch all tasks
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    Iterable<Task> getAllTasks() {
        return taskService.findAll();
    }

    // Get tasks by status value
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tasks/{status}", method = RequestMethod.GET)
    List<Task> getTasksByStatusValue(@PathVariable String status) throws Exception {
        return taskService.findByStatus(status);
    }

    // Get tasks not completed
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tasks/notcompleted", method = RequestMethod.GET)
    List<Task> getTasksNotCompleted() {
        return taskService.findByStatusNot(TaskStatus.complete);
    }

    // Creating a task
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tasks/add/users/{userId}", method = RequestMethod.POST)
    Task createTask(@RequestBody Task newTask, @PathVariable Long userId) throws Exception {
        return taskService.CreateOrUpdateTask(userId, newTask);
    }

    // Updating a task
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/tasks/update/users/{userId}", method = RequestMethod.PUT)
    Task updateTask(@RequestBody Task updatedTask, @PathVariable Long userId) throws Exception {
        return taskService.CreateOrUpdateTask(userId, updatedTask);
    }
}
