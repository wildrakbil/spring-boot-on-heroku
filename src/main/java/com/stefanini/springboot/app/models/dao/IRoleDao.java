package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDao extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
