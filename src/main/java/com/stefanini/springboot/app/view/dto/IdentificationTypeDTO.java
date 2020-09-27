package com.stefanini.springboot.app.view.dto;

import java.util.Date;

public class IdentificationTypeDTO {


    private Long id;
    private String name;
    private Date createDay;
    private String createUser;
    private Date modificationDay;
    private String modificationUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDay() {
        return createDay;
    }

    public void setCreateDay(Date createDay) {
        this.createDay = createDay;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModificationDay() {
        return modificationDay;
    }

    public void setModificationDay(Date modificationDay) {
        this.modificationDay = modificationDay;
    }

    public String getModificationUser() {
        return modificationUser;
    }

    public void setModificationUser(String modificationUser) {
        this.modificationUser = modificationUser;
    }
}
