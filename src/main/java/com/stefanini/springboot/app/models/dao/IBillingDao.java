package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBillingDao extends JpaRepository<Billing, Long> {

    Billing findById(long id);
}
