package com.malynovsky.task.controller;

import com.malynovsky.task.entity.User;
import com.malynovsky.task.exception.InvalidInputException;
import com.malynovsky.task.exception.UserNotFoundException;
import com.malynovsky.task.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable(name = "userId") Integer userId) {
        if (userId == null) {
            throw new InvalidInputException("User id can't be null.");
        }

        return userService.getUserById(userId);
    }

    @GetMapping()
    public User getUser(@RequestParam(name = "email") String email) {
        if (email == null) {
            throw new InvalidInputException("User email can't be null.");
        }

        return userService.getUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping(consumes = "application/json")
    public int createUser(@RequestBody() User user) {
        return userService.save(user);
    }

    @PutMapping(consumes = "application/json")
    public int updateUser(@RequestBody() User user) {
        return userService.update(user);
    }

    @DeleteMapping()
    public void deleteUser(@RequestParam(name = "email") String email) {
        if (email == null) {
            throw new InvalidInputException("User email can't be null.");
        }

        userService.delete(email);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable(name = "userId") Integer userId) {
        if (userId == null) {
            throw new InvalidInputException("User id can't be null.");
        }

        userService.delete(userId);
    }
}
