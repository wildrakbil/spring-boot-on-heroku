package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.Role;
import com.stefanini.springboot.app.models.entity.User;
import com.stefanini.springboot.app.view.dto.RoleDTO;
import com.stefanini.springboot.app.view.dto.UserDTO;

public interface IMapper {

    UserDTO mapPerson(User in);
    User mapPerson(UserDTO in);
    RoleDTO mapRole(Role in);
    Role mapRole(RoleDTO in);
}
