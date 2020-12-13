package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface IBookingDao  extends JpaRepository<Booking, Long> {

    Booking findById(long id);

    @Query(value = "SELECT * FROM booking WHERE day = ?1 AND start_time = ?2", nativeQuery = true)
    Booking findByDayAndStartTime(String day, long startTime);
}
