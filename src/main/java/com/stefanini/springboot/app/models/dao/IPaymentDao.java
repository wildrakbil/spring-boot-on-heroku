package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentDao extends JpaRepository<Payment, Long> {

    Payment findById(long id);
}
