package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.auth.service.JWTService;
import com.stefanini.springboot.app.models.dao.IPersonDao;
import com.stefanini.springboot.app.models.entity.IdentificationType;
import com.stefanini.springboot.app.models.entity.Person;
import com.stefanini.springboot.app.models.entity.State;
import com.stefanini.springboot.app.view.dto.IdentificationTypeDTO;
import com.stefanini.springboot.app.view.dto.PersonDTO;
import com.stefanini.springboot.app.view.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private IPersonDao personDao;

    @Resource(name = "mapper")
    private IMapper mapper;

    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Secured("ROLE_USER")
    @GetMapping("/person/{id}")
    public PersonDTO show(@PathVariable long id) {
        Person person = personDao.findById(id);
        if (person == null) {
            //throw new personalException( "Persona con ID:".concat(String.valueOf(id)).concat(" No encontrado"));
        }
        return mapper.mapPerson(person);
    }

    @Secured("ROLE_USER")
    @PostMapping("/person")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> savePerson(@RequestBody PersonDTO person, BindingResult result, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        Person personNew = null;
        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setIdentificationTypeCode(new IdentificationTypeDTO());
            person.getIdentificationTypeCode().setCreateDay(new Date());
            authentication.getPrincipal();
            person.getIdentificationTypeCode().setCreateUser(authentication.getName());
            personNew = personDao.save(mapper.mapPerson(person));
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El cliente ha sido creado con éxito!");
        response.put("person", personNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_USER")
    @PutMapping("/person/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PersonDTO in, BindingResult result) {
        Person personActual = personDao.findById(id);
        Person personUpdated = null;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {

            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (personActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
                    .concat(String.valueOf(id).concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        try {
            personActual.setFirstname(in.getFirstname());
            personActual.setLastname(in.getLastname());
            personActual.setBirthday(in.getBirthday());
            personActual.setUsername(in.getUsername());
            personActual.setPassword(in.getPassword());
            personActual.setIdentification(in.getIdentification());
            personActual.setIdentificationTypeCode(new IdentificationType());
            if (in.getIdentificationTypeCode() != null) {
                personActual.getIdentificationTypeCode().setId(in.getIdentificationTypeCode().getId());
                personActual.getIdentificationTypeCode().setName(in.getIdentificationTypeCode().getName());
                personActual.getIdentificationTypeCode().setCreateDay(in.getIdentificationTypeCode().getCreateDay());
                personActual.getIdentificationTypeCode().setCreateUser(in.getIdentificationTypeCode().getCreateUser());
                personActual.getIdentificationTypeCode().setModificationDay(in.getIdentificationTypeCode().getModificationDay());
                personActual.getIdentificationTypeCode().setModificationUser(in.getIdentificationTypeCode().getModificationUser());
            }
            personActual.setStateCode(new State());
            if (in.getStateCode() != null) {
                personActual.getStateCode().setId(in.getStateCode().getId());
                personActual.getStateCode().setName(in.getStateCode().getName());
                personActual.getStateCode().setCreateDay(in.getStateCode().getCreateDay());
                personActual.getStateCode().setCreateUser(in.getStateCode().getCreateUser());
                personActual.getStateCode().setModificationDay(in.getStateCode().getModificationDay());
                personActual.getStateCode().setModificationUser(in.getStateCode().getModificationUser());

            }
            personUpdated = personDao.save(personActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el cliente en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El cliente ha sido actualizado con éxito!");
        response.put("person", personUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_USER")
    @DeleteMapping("/person/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        Person person = personDao.findById(id);
        personDao.delete(person);
    }
}
