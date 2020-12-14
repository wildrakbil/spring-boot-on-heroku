package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.models.dao.IServiceDao;
import com.stefanini.springboot.app.models.entity.Service;
import com.stefanini.springboot.app.util.IUtils;
import com.stefanini.springboot.app.view.dto.ServiceDTO;
import com.stefanini.springboot.app.view.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
public class ServiceController {

    @Resource(name = "mapper")
    private IMapper mapper;

    @Autowired
    private IUtils utils;

    @Autowired
    private IServiceDao serviceDao;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/services")
    public List<ServiceDTO> getAllServices() {
        List<Service> data = serviceDao.findAll();
        List<ServiceDTO> out = new ArrayList<>();
        if (data != null) {
            for (Service in : data) {
                out.add(mapper.mapService(in));
            }
        }
        return out;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/service/{id}")
    public ServiceDTO getService(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Service out = serviceDao.findById(id);
        if (out == null) {
            response.put("mensaje", "Servicio con ID:".concat(String.valueOf(id)).concat(" No encontrado"));
        }
        return mapper.mapService(out);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/service")
    public ResponseEntity<?> createService(@RequestBody ServiceDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Service serveceNew = null;
        if (utils.validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            serveceNew = serviceDao.save(mapper.mapService(in));
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El servicio ha sido creado con éxito!");
        response.put("data", serveceNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping("/service/{id}")
    public ResponseEntity<?> updateService(@PathVariable long id, @RequestBody ServiceDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Service serviceUpdated = null;
        if (utils.validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Service serviceActual = serviceDao.findById(id);
            if (serviceActual == null) {
                response.put("mensaje", "Error: no se pudo editar, el usuario con ID: "
                        .concat(String.valueOf(id).concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            in.setId(serviceActual.getId());
            serviceActual = mapper.mapService(in);
            serviceUpdated = serviceDao.save(serviceActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el servicio en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El servicio ha sido actualizado con éxito!");
        response.put("data", serviceUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/service/{id}")
    public ResponseEntity<?> deleteservice(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Service service = serviceDao.findById(id);
        if (service != null) {
            serviceDao.delete(service);
        } else {
            response.put("mensaje", "La Servicio no fue encontrado para ser borrado");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
