package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.models.dao.IBookingDao;
import com.stefanini.springboot.app.models.entity.Booking;
import com.stefanini.springboot.app.models.entity.Role;
import com.stefanini.springboot.app.view.dto.BookingDTO;
import com.stefanini.springboot.app.view.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:4200", "https://angular-on-heroku1.herokuapp.com/"})
@RestController
@RequestMapping("/api")
public class BookingController {

    @Resource(name = "mapper")
    private IMapper mapper;

    @Autowired
    private IBookingDao bookingDao;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/bookings")
    public List<BookingDTO> getAllBookings() {
        List<Booking> data = bookingDao.findAll();
        List<BookingDTO> out = new ArrayList<>();
        if (data != null) {
            for (Booking in : data) {
                out.add(mapper.mapBooking(in));
            }
        }
        return out;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/booking/{id}")
    public BookingDTO getBooking(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Booking out = bookingDao.findById(id);
        if (out == null) {
            response.put("mensaje", "La reserva con ID:".concat(String.valueOf(id)).concat(" No encontrado"));
        }
        return mapper.mapBooking(out);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/booking")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Booking bookingNew = null;
        validBindingResult(result, response);
        if (validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Tomorrow(in);
            if (validSchedule(in)) {

                response.put("mensaje", String.format("El horario %d del %s no esta disponible", in.getStartTime(), dateFormat.format(in.getDay())));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
            } else {
                bookingNew = bookingDao.save(mapper.mapBooking(in));
            }
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La reserva ha sido creado con éxito!");
        response.put("data", bookingNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping("/booking/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable long id, @RequestBody BookingDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Booking bookingUpdated = null;
        if (validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Booking serviceActual = bookingDao.findById(id);
            if (serviceActual == null) {
                response.put("mensaje", "Error: no se pudo editar, la reserva con ID: "
                        .concat(String.valueOf(id).concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            in.setId(serviceActual.getId());
            serviceActual = mapper.mapBooking(in);
            bookingUpdated = bookingDao.save(serviceActual);
            bookingUpdated.getUser().setRole(new Role());
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el servicio en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El servicio ha sido actualizado con éxito!");
        //response.put("data", bookingUpdated);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/booking/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Booking booking = bookingDao.findById(id);
        if (booking != null) {
            bookingDao.delete(booking);
        } else {
            response.put("mensaje", "La reserva no fue encontrada para ser borrada");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    private boolean validBindingResult(BindingResult result, Map<String, Object> response) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return true;
        }
        return false;
    }

    private boolean validSchedule(BookingDTO in) {
        String date = dateFormat.format(in.getDay());
        Booking booking = bookingDao.findByDayAndStartTime(date, in.getStartTime());
        if (booking != null) {
            return true;
        }
        return false;
    }

    private void Tomorrow(BookingDTO in) {
        Calendar c = Calendar.getInstance();
        c.setTime(in.getDay());
        c.add(Calendar.DATE, 1);
        in.setDay(c.getTime());
    }
}
