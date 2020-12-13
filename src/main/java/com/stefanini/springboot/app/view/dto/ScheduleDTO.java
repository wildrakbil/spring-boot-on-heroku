package com.stefanini.springboot.app.view.dto;

import java.io.Serializable;

public class ScheduleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String day;
    private long startTime;
    private long endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
}
