package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.models.dao.IBillingDao;
import com.stefanini.springboot.app.models.entity.Billing;
import com.stefanini.springboot.app.util.*;
import com.stefanini.springboot.app.util.payment.EFE;
import com.stefanini.springboot.app.util.payment.PSE;
import com.stefanini.springboot.app.util.payment.PaymentMethod;
import com.stefanini.springboot.app.util.payment.TDC;
import com.stefanini.springboot.app.view.dto.BillingDTO;
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
public class BillingController {

    @Resource(name = "mapper")
    private IMapper mapper;

    @Autowired
    private IUtils utils;

    @Autowired
    private IBillingDao billingDao;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/billings")
    public List<BillingDTO> getAllBillings() {
        List<Billing> data = billingDao.findAll();
        List<BillingDTO> out = new ArrayList<>();
        if (data != null) {
            for (Billing in : data) {
                if (!in.getState()) {
                    out.add(mapper.mapBilling(in));
                }
            }
        }
        return out;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/billing/{id}")
    public BillingDTO getBilling(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Billing out = billingDao.findById(id);
        if (out == null) {
            response.put("mensaje", "Factura con ID:".concat(String.valueOf(id)).concat(" No encontrada"));
        }
        return mapper.mapBilling(out);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/billing")
    public ResponseEntity<?> createBilling(@RequestBody BillingDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Billing billingNew = null;
        if (utils.validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            in.setState(false); // factura pendiente de pago
            billingNew = billingDao.save(mapper.mapBilling(in));
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "La factura ha sido creada con éxito!");
        response.put("data", billingNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }


    @Secured("ROLE_ADMIN")
    @PutMapping("/billing/{id}")
    public ResponseEntity<?> updateBilling(@PathVariable long id, @RequestBody BillingDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        Billing billingUpdated = null;
        if (utils.validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Billing billingActual = billingDao.findById(id);
            if (billingActual == null) {
                response.put("mensaje", "Error: no se pudo editar, la factura con ID: "
                        .concat(String.valueOf(id).concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            in.setId(billingActual.getId());
            billingActual = mapper.mapBilling(in);
            billingActual.setState(false); // factura pendiente de pago
            billingUpdated = billingDao.save(billingActual);

        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar la factura en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "El servicio ha sido actualizado con éxito!");
        response.put("data", mapper.mapBilling(billingUpdated));

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/billing/{id}")
    public ResponseEntity<?> deleteBilling(@PathVariable long id) {
        Map<String, Object> response = new HashMap<>();
        Billing billing = billingDao.findById(id);
        if (billing != null) {
            billingDao.delete(billing);
        } else {
            response.put("mensaje", "La Factura no fue encontrada para ser borrado");
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PutMapping("/pay")
    public ResponseEntity<?> payBill(@RequestBody BillingDTO in, BindingResult result) {
        Map<String, Object> response = new HashMap<>();
        if (utils.validBindingResult(result, response)) {
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Billing billingActual = billingDao.findById((long) in.getId());
            if (billingActual == null) {
                response.put("mensaje", "Error: no se encontro la factura con ID: "
                        .concat(String.valueOf(in.getId()).concat(" no existe en la base de datos!")));
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            PaymentMethod paymentMethod = createPayment(billingActual.getPayment().getType(), response);
            if (paymentMethod == null) {
                return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
            }
            String mensaje = "";
            if (billingActual.getState()) {
                mensaje = "Esta factura ya fue pagada";
            } else {
                mensaje = paymentMethod.payBill(billingActual.getAmount());
            }
            response.put("mensaje", mensaje);
            billingActual.setState(true); //factura pagada
            billingDao.save(billingActual);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al efectuar el pago");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    private PaymentMethod createPayment(String type, Map<String, Object> response) {
        PaymentMethod out = null;
        switch (type) {
            case "TDC":
                out = new TDC();
                break;
            case "PSE":
                out = new PSE();
                break;
            case "EFE":
                out = new EFE();
                break;
            default:
                response.put("mensaje", "Error: Modo de pago invalildo ");
        }
        return out;
    }
}
