package springboot.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springboot.example.models.User;
import springboot.example.services.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    // Getting all users
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Iterable<User> getAllUsers() {
        return userService.findAll();
    }

    // Getting an user by id
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/users/{idOrUserName}", method = RequestMethod.GET)
    User getUserByIdOrUserName(@PathVariable String idOrUserName) {
        return userService.findByIdOrUserName(idOrUserName);
    }

    // Create a user
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    User createUser(@RequestBody User newUser) {
        return userService.createUser(newUser.getUserName());
    }

    // Updating a user
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) throws Exception {
        return userService.updateUser(userId, updatedUser.getUserName());
    }

    // Deleting by User ID
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/users/{userId}", method = RequestMethod.DELETE)
    void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
