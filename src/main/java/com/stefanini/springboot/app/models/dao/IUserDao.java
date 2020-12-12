package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserDao extends JpaRepository<User, Long> {

    User findById(long id);
    User findByUsername(String username);
}

