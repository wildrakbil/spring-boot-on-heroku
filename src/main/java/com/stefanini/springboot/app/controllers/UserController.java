package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.auth.service.JWTService;
import com.stefanini.springboot.app.models.dao.IRoleDao;
import com.stefanini.springboot.app.models.dao.IUserDao;
import com.stefanini.springboot.app.models.entity.Role;
import com.stefanini.springboot.app.models.entity.User;
import com.stefanini.springboot.app.util.IUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://angular-on-heroku1.herokuapp.com/"})
@RestController
@RequestMapping("/api")
public class UserController {

    @Resource(name = "mapper")
    private IMapper mapper;

    @Autowired
    private IUtils utils;

    private JWTService jwtService;

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Secured({"ROLE_ADMIN"})
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getAllUsers() {
        List<User> userList = userDao.findAll();
        List<UserDTO> out = new ArrayList<>();
        if (userList != null) {
            for (User in : userList) {
                out.add(mapper.mapUser(in));
            }
        }
        return out;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/user/{id}")
    public UserDTO getUser(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        User out = userDao.findById(id);
        if (out == null) {
            response.put("mensaje", "Usuario con ID:".concat(String.valueOf(id)).concat(" No encontrado"));
        }
        return mapper.mapUser(out);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserDTO user, BindingResult result, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        User userNew = null;
        if (utils.validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Role role = roleDao.findByName(user.getRole().getName());
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(mapper.mapRole(role));
            userNew = userDao.save(mapper.mapUser(user));
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El usuario ha sido creado con éxito!");
        response.put("data", userNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody UserDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        User userUpdated = null;
        if (utils.validBindingResult(result, response)) {
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
            userActual = mapper.mapUser(in);
            if (in.getRole() != null) {
                Role role = roleDao.findByName(in.getRole().getName());
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
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        User user = userDao.findById(id);
        if (user != null) {
            userDao.delete(user);
        } else {
            response.put("mensaje", "El usuario no fue encontrados para ser borrado");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
