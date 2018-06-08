package springboot.example.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springboot.example.models.TodoList;
import springboot.example.services.TodoListService;

@RestController
public class TodoListController {
    public static final Logger logger = LoggerFactory.getLogger(TodoListController.class);

    @Autowired
    private TodoListService todoListService;

    // Creating a todo list
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/todolists", method = RequestMethod.POST)
    TodoList createUser(@RequestBody TodoList newTodoList) {
        return todoListService.saveTodoList(newTodoList);
    }

    // Adding task to a todo list
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/todolists/{id}/addtask", method = RequestMethod.POST)
    TodoList AddTask(@PathVariable Long id, @RequestBody List<String> taskIds) throws Exception {
        List<Long> tasksIds = taskIds.stream().map(Long::parseLong).collect(Collectors.toList());
        return todoListService.addTasks(id, tasksIds);
    }

    // Fetch all todo lists with tasks (List all tasks grouped by todo list)
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/todolists", method = RequestMethod.GET)
    Iterable<TodoList> getAllTasks() {
        return todoListService.listAllTasksGroupedByTodoList();
    }

    // Fetch a particular todo list including all its tasks
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/todolists/{id}", method = RequestMethod.GET)
    TodoList getTodoListById(@PathVariable Long id) throws Exception {
        return todoListService.findTodoListById(id);
    }

    // Delete TodoList by Id
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/todolists/{id}", method = RequestMethod.DELETE)
    void deleteTodoList(@PathVariable Long id) throws Exception {
        todoListService.deleteTodoListById(id);
    }

    // Delete tasks from a todo list
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/todolists/{id}/removetasks", method = RequestMethod.DELETE)
    TodoList deleteTasksFromTodoList(@PathVariable Long id, @RequestBody List<String> taskIds) throws Exception {
        List<Long> removeTasksIds = taskIds.stream().map(Long::parseLong).collect(Collectors.toList());
        return todoListService.deleteTasksInTodoList(id, removeTasksIds);
    }

}
