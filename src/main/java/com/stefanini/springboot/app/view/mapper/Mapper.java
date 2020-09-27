package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.IdentificationType;
import com.stefanini.springboot.app.models.entity.Person;
import com.stefanini.springboot.app.models.entity.State;
import com.stefanini.springboot.app.view.dto.IdentificationTypeDTO;
import com.stefanini.springboot.app.view.dto.PersonDTO;
import com.stefanini.springboot.app.view.dto.StateDTO;
import org.springframework.stereotype.Component;


@Component(value = "mapper")
public class Mapper implements IMapper{
    @Override
    public PersonDTO mapPerson(Person in) {
        if(in==null){
            return null;
        }
        PersonDTO out= new PersonDTO();
        out.setId(in.getId());
        out.setFirstname(in.getFirstname());
        out.setLastname(in.getLastname());
        out.setBirthday(in.getBirthday());
        out.setUsername(in.getUsername());
        out.setPassword(in.getPassword());
        out.setIdentification(in.getIdentification());
        out.setIdentificationTypeCode(mapIdentificationType(in.getIdentificationTypeCode()));
        out.setStateCode(mapState(in.getStateCode()));
        return out;
    }

    @Override
    public Person mapPerson(PersonDTO in) {
        if(in==null){
            return null;
        }
        Person out= new Person();
        out.setId(in.getId());
        out.setFirstname(in.getFirstname());
        out.setLastname(in.getLastname());
        out.setBirthday(in.getBirthday());
        out.setUsername(in.getUsername());
        out.setPassword(in.getPassword());
        out.setIdentification(in.getIdentification());
        out.setIdentificationTypeCode(mapIdentificationType(in.getIdentificationTypeCode()));
        out.setStateCode(mapState(in.getStateCode()));
        return out;
    }

    private IdentificationTypeDTO mapIdentificationType(IdentificationType in){
        if(in==null){
            return null;
        }
        IdentificationTypeDTO out = new IdentificationTypeDTO();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setCreateDay(in.getCreateDay());
        out.setCreateUser(in.getCreateUser());
        out.setModificationDay(in.getModificationDay());
        out.setModificationUser(in.getModificationUser());
        return out;
    }

    private IdentificationType mapIdentificationType(IdentificationTypeDTO in){
        if(in==null){
            return null;
        }
        IdentificationType out = new IdentificationType();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setCreateDay(in.getCreateDay());
        out.setCreateUser(in.getCreateUser());
        out.setModificationDay(in.getModificationDay());
        out.setModificationUser(in.getModificationUser());
        return out;
    }

    private State mapState(StateDTO in){
        if(in==null){
            return null;
        }
        State out = new State();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setCreateDay(in.getCreateDay());
        out.setCreateUser(in.getCreateUser());
        out.setModificationDay(in.getModificationDay());
        out.setModificationUser(in.getModificationUser());
        return out;
    }

    private StateDTO mapState(State in){
        if(in==null){
            return null;
        }
        StateDTO out = new StateDTO();
        out.setId(in.getId());
        out.setName(in.getName());
        out.setCreateDay(in.getCreateDay());
        out.setCreateUser(in.getCreateUser());
        out.setModificationDay(in.getModificationDay());
        out.setModificationUser(in.getModificationUser());
        return out;
    }
}
