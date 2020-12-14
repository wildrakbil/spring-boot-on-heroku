package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.models.dao.IScheduleDao;
import com.stefanini.springboot.app.models.entity.Schedule;
import com.stefanini.springboot.app.util.IUtils;
import com.stefanini.springboot.app.view.dto.ScheduleDTO;
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
public class ScheduleController {

    @Resource(name = "mapper")
    private IMapper mapper;

    @Autowired
    private IUtils utils;

    @Autowired
    private IScheduleDao scheduleDao;


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/schedules")
    @ResponseStatus(HttpStatus.OK)
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> scheduleList = scheduleDao.findAll();
        List<ScheduleDTO> out = new ArrayList<>();
        if (scheduleList != null) {
            for (Schedule in : scheduleList) {
                out.add(mapper.mapSchedule(in));
            }
        }
        return out;
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/schedule/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable long id, @RequestBody ScheduleDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Schedule scheduleUpdated = null;
        if (utils.validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Schedule schedule = scheduleDao.findById(id);
            if (schedule == null) {
                response.put("mensaje", "Error: no se pudo editar, el horario con ID: "
                        .concat(String.valueOf(id).concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            in.setId(schedule.getId());
            schedule = mapper.mapSchedule(in);
            scheduleDao.save(schedule);
            scheduleUpdated = scheduleDao.save(schedule);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el usuario en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El horario ha sido actualizado con éxito!");
        response.put("data", scheduleUpdated);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
