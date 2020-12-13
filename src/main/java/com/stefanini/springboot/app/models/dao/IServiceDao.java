package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.Service;
import com.stefanini.springboot.app.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceDao extends JpaRepository<Service, Long> {

    Service findById(long id);
}
