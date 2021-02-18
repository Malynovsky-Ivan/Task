package com.malynovsky.task.service.impl;

import com.malynovsky.task.dao.UserRepository;
import com.malynovsky.task.entity.User;
import com.malynovsky.task.exception.InvalidInputException;
import com.malynovsky.task.exception.UserNotFoundException;
import com.malynovsky.task.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }

    @Override
    public User getUserById(int userId) {
        return repository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public int save(User user) {
        if (user == null || user.getId() != 0) {
            throw new InvalidInputException("Invalid parameter.");
        }
        if (repository.existsByEmail(user.getEmail())) {
            throw new InvalidInputException("User with such email is already exists.");
        }
        return repository.save(user).getId();
    }

    @Override
    public int update(User user) {
        if (user == null || user.getId() == 0) {
            throw new InvalidInputException();
        }
        if (!repository.existsById(user.getId())) {
            throw new UserNotFoundException();
        }
        return repository.save(user).getId();
    }

    @Override
    public void delete(int id) {
        if (!repository.existsById(id)) {
            logger.warn("User with given id {} is not exists", id);
            throw new UserNotFoundException();
        }
        repository.deleteById(id);
    }

    @Override
    public void delete(String email) {
        if (!repository.existsByEmail(email)) {
            logger.warn("User with given email {} is not exists", email);
            throw new UserNotFoundException();
        }
        repository.deleteByEmail(email);
    }
}
