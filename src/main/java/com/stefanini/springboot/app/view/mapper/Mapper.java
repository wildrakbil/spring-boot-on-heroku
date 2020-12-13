package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.Role;
import com.stefanini.springboot.app.models.entity.Schedule;
import com.stefanini.springboot.app.models.entity.User;
import com.stefanini.springboot.app.view.dto.RoleDTO;
import com.stefanini.springboot.app.view.dto.ScheduleDTO;
import com.stefanini.springboot.app.view.dto.UserDTO;
import org.springframework.stereotype.Component;


@Component(value = "mapper")
public class Mapper implements IMapper {
    @Override
    public UserDTO mapUser(User in) {
        if (in == null) {
            return null;
        }
        UserDTO out = new UserDTO();
        out.setId(in.getId());
        out.setRole(mapRole(in.getRole()));
        out.setUsername(in.getUsername());
        out.setPassword(in.getPassword());
        out.setCreateday(in.getCreateday());
        out.setUpdateat(in.getUpdateat());
        out.setFirstname(in.getFirstname());
        out.setLastname(in.getLastname());
        out.setBirthday(in.getBirthday());
        out.setIdentification(in.getIdentification());
        out.setPhone(in.getPhone());
        out.setAddress(in.getAddress());
        return out;
    }

    @Override
    public User mapUser(UserDTO in) {
        if (in == null) {
            return null;
        }
        User out = new User();
        out.setId(in.getId());
        out.setFirstname(in.getFirstname());
        out.setLastname(in.getLastname());
        out.setBirthday(in.getBirthday());
        out.setUsername(in.getUsername());
        out.setPassword(in.getPassword());
        out.setIdentification(in.getIdentification());
        out.setRole(mapRole(in.getRole()));
        return out;
    }

    @Override
    public RoleDTO mapRole(Role in) {
        if (in == null) {
            return null;
        }
        RoleDTO out = new RoleDTO();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setPermits(in.getPermits());
        return out;
    }

    @Override
    public Role mapRole(RoleDTO in) {
        if (in == null) {
            return null;
        }
        Role out = new Role();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setPermits(in.getPermits());
        return out;
    }

    @Override
    public Schedule mapSchedule(ScheduleDTO in) {
        if (in == null) {
            return null;
        }
        Schedule out = new Schedule();
        out.setId(in.getId());
        out.setDay(in.getDay());
        out.setStartTime(in.getStartTime());
        out.setEndTime(in.getEndTime());
        return out;
    }

    @Override
    public ScheduleDTO mapSchedule(Schedule in) {
        if (in == null) {
            return null;
        }
        ScheduleDTO out = new ScheduleDTO();
        out.setId(in.getId());
        out.setDay(in.getDay());
        out.setStartTime(in.getStartTime());
        out.setEndTime(in.getEndTime());
        return out;
    }
}
