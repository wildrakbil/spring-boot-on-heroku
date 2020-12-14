package com.stefanini.springboot.app.controllers;

import com.stefanini.springboot.app.models.dao.IPaymentDao;
import com.stefanini.springboot.app.models.entity.Payment;
import com.stefanini.springboot.app.util.IUtils;
import com.stefanini.springboot.app.view.dto.PaymentDTO;
import com.stefanini.springboot.app.view.mapper.IMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://angular-on-heroku1.herokuapp.com/"})
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Resource(name = "mapper")
    private IMapper mapper;

    @Autowired
    private IUtils utils;

    @Autowired
    private IPaymentDao paymentDao;

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/payments")
    public List<PaymentDTO> getAllPayments() {
        List<Payment> data = paymentDao.findAll();
        List<PaymentDTO> out = new ArrayList<>();
        if (data != null) {
            for (Payment in : data) {
                out.add(mapper.mapPayment(in));
            }
        }
        return out;
    }
}
