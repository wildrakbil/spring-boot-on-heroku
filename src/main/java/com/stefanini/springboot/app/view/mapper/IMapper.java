package com.stefanini.springboot.app.view.mapper;

import com.stefanini.springboot.app.models.entity.Person;
import com.stefanini.springboot.app.view.dto.PersonDTO;

public interface IMapper {

    PersonDTO mapPerson(Person in);
    Person mapPerson(PersonDTO in);
}
