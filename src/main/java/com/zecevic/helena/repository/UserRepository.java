package com.zecevic.helena.repository;

import com.zecevic.helena.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findById(long id);

    List<User> findAll();

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
