package com.malynovsky.task.service;

import com.malynovsky.task.entity.User;

import java.util.Optional;

public interface UserService {

    /**
     * Returns user by given email.
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Returns user by given id.
     */
    User getUserById(int userId);

    /**
     * Save given user.
     */
    int save(User user);

    /**
     * Update given user.
     */
    int update(User user);

    /**
     * Delete by id.
     */
    void delete(int id);

    /**
     * Delete by email.
     */
    void delete(String email);
}
