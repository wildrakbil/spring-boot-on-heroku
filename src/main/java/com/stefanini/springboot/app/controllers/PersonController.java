package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.models.dao.IPersonDao;
import com.stefanini.springboot.app.models.entity.IdentificationType;
import com.stefanini.springboot.app.models.entity.Person;
import com.stefanini.springboot.app.models.entity.State;
import com.stefanini.springboot.app.view.dto.PersonDTO;
import com.stefanini.springboot.app.view.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private IPersonDao personDao;

    @Resource(name = "mapper")
    private IMapper mapper;

    @Secured("ROLE_USER")
    @GetMapping("/persons")
    public List<PersonDTO> getAll() {
        List<Person> personList = personDao.findAll();
        List<PersonDTO> out = new ArrayList<>();
        if (personList != null) {
            for (Person in : personList) {
                out.add(mapper.mapPerson(in));
            }
        }
        return out;
    }

    @GetMapping("/person/{id}")
    public PersonDTO show(@PathVariable long id) {
        Person person = personDao.findById(id);
        if(person==null){
            //throw new personalException( "Persona con ID:".concat(String.valueOf(id)).concat(" No encontrado"));
        }
        return mapper.mapPerson(person);
    }

    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public void savePerson(@RequestBody PersonDTO person) {
        personDao.save(mapper.mapPerson(person));
    }

    @PutMapping("/person/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@PathVariable long id, @RequestBody PersonDTO in) {
        Person out = personDao.findById(id);
        out.setFirstname(in.getFirstname());
        out.setLastname(in.getLastname());
        out.setBirthday(in.getBirthday());
        out.setUsername(in.getUsername());
        out.setPassword(in.getPassword());
        out.setIdentification(in.getIdentification());
        out.setIdentificationTypeCode(new IdentificationType());
        if(in.getIdentificationTypeCode()!=null) {
            out.getIdentificationTypeCode().setId(in.getIdentificationTypeCode().getId());
            out.getIdentificationTypeCode().setName(in.getIdentificationTypeCode().getName());
            out.getIdentificationTypeCode().setCreateDay(in.getIdentificationTypeCode().getCreateDay());
            out.getIdentificationTypeCode().setCreateUser(in.getIdentificationTypeCode().getCreateUser());
            out.getIdentificationTypeCode().setModificationDay(in.getIdentificationTypeCode().getModificationDay());
            out.getIdentificationTypeCode().setModificationUser(in.getIdentificationTypeCode().getModificationUser());
        }
        out.setStateCode(new State());
        if(in.getStateCode()!=null){
            out.getStateCode().setId(in.getStateCode().getId());
            out.getStateCode().setName(in.getStateCode().getName());
            out.getStateCode().setCreateDay(in.getStateCode().getCreateDay());
            out.getStateCode().setCreateUser(in.getStateCode().getCreateUser());
            out.getStateCode().setModificationDay(in.getStateCode().getModificationDay());
            out.getStateCode().setModificationUser(in.getStateCode().getModificationUser());

        }
        personDao.save(out);
    }

    @DeleteMapping("/person/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        Person person = personDao.findById(id);
        personDao.delete(person);
    }
}
