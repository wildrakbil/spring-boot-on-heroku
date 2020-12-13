package com.stefanini.springboot.app.view.dto;

import java.io.Serializable;
import java.util.Date;

public class BookingDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private UserDTO user;
    private ServiceDTO service;
    private Date day;
    private long startTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public ServiceDTO getService() {
        return service;
    }

    public void setService(ServiceDTO service) {
        this.service = service;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
