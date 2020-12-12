package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.Role;
import com.stefanini.springboot.app.models.entity.User;
import com.stefanini.springboot.app.view.dto.RoleDTO;
import com.stefanini.springboot.app.view.dto.UserDTO;
import org.springframework.stereotype.Component;


@Component(value = "mapper")
public class Mapper implements IMapper{
    @Override
    public UserDTO mapPerson(User in) {
        if(in==null){
            return null;
        }
        UserDTO out= new UserDTO();
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
    public User mapPerson(UserDTO in) {
        if(in==null){
            return null;
        }
        User out= new User();
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
    public RoleDTO mapRole(Role in){
        if(in==null){
            return null;
        }
        RoleDTO out = new RoleDTO();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setPermits(in.getPermits());
        return out;
    }

    @Override
    public Role mapRole(RoleDTO in){
        if(in==null){
            return null;
        }
        Role out = new Role();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setPermits(in.getPermits());
        return out;
    }

}
