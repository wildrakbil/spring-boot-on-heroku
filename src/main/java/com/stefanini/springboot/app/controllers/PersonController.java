package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.auth.service.JWTService;
import com.stefanini.springboot.app.models.dao.IRoleDao;
import com.stefanini.springboot.app.models.dao.IUserDao;
import com.stefanini.springboot.app.models.entity.Role;
import com.stefanini.springboot.app.models.entity.User;
import com.stefanini.springboot.app.view.dto.UserDTO;
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

@CrossOrigin(origins = {"http://localhost:4200","https://angular-on-heroku1.herokuapp.com/"})
@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Resource(name = "mapper")
    private IMapper mapper;

    private JWTService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/persons")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO>  getAllPerson() {
        List<User> userList = userDao.findAll();
        List<UserDTO> out = new ArrayList<>();
        if (userList != null) {
            for (User in : userList) {
                out.add(mapper.mapPerson(in));
            }
        }
        return out;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getPerson(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        User out = userDao.findById(id);
        if (out == null) {
            response.put("mensaje", "Usuario con ID:".concat(String.valueOf(id)).concat(" No encontrado"));
            //return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NO_CONTENT);
        }
        return mapper.mapPerson(out);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/person")
    public ResponseEntity<?> createPerson(@RequestBody UserDTO person, BindingResult result, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        User userNew = null;
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Role role = roleDao.findByname(person.getRole().getName());
            person.setPassword(passwordEncoder.encode(person.getPassword()));
            person.setRole(mapper.mapRole(role));
            userNew = userDao.save(mapper.mapPerson(person));
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido creado con éxito!");
        response.put("data", userNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/person/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        User userUpdated = null;
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            User userActual = userDao.findById(id);
            if (userActual == null) {
                response.put("mensaje", "Error: no se pudo editar, el usuario con ID: "
                        .concat(String.valueOf(id).concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            in.setId(userActual.getId());
            userActual = mapper.mapPerson(in);
            if (in.getRole() != null) {
                Role role = roleDao.findByname(in.getRole().getName());
                userActual.setRole(role);
            }
            userUpdated = userDao.save(userActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El usuario ha sido actualizado con éxito!");
        response.put("data", userUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/person/{id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        User user = userDao.findById(id);
        userDao.delete(user);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
