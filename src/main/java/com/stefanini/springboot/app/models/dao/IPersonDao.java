package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPersonDao extends JpaRepository<Person, Long> {

    Person findById(long id);
    Person findByUsername(String username);
}

