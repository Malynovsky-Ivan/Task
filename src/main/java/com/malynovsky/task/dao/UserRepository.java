package com.malynovsky.task.dao;

import com.malynovsky.task.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByEmail(String email);
    
    boolean existsByEmail(String email);

    void deleteByEmail(String email);
}
