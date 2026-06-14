package com.example.travelplanner.controllers;

import com.example.travelplanner.models.User;
import com.example.travelplanner.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")

    public List<User> getAllUsers() {
       return userService.getAllUsers();
    }

    @PostMapping("/users")

    public User addUser( @RequestBody User user) {
     return userService.addUser(user);
    }

    @GetMapping("/users/{id}")

    public void deleteUser(@PathVariable Integer id) {
       userService.deleteUser(id);
    }
}