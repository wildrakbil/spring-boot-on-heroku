package com.stefanini.springboot.app.models.dao;

import com.stefanini.springboot.app.models.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IScheduleDao extends JpaRepository<Schedule, Long> {

    Schedule findById(long id);
}
