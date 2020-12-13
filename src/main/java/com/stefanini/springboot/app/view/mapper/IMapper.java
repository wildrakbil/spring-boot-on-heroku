package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.Role;
import com.stefanini.springboot.app.models.entity.Schedule;
import com.stefanini.springboot.app.models.entity.User;
import com.stefanini.springboot.app.view.dto.RoleDTO;
import com.stefanini.springboot.app.view.dto.ScheduleDTO;
import com.stefanini.springboot.app.view.dto.UserDTO;

public interface IMapper {

    UserDTO mapUser(User in);
    User mapUser(UserDTO in);
    RoleDTO mapRole(Role in);
    Role mapRole(RoleDTO in);
    Schedule mapSchedule(ScheduleDTO in);
    ScheduleDTO mapSchedule(Schedule in);
}
