package com.stefanini.springboot.app.view.dto;

import java.io.Serializable;
import java.util.Date;

public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstname;
    private String lastname;
    private Date birthday;
    private String username;
    private String password;
    private Long identification;
    private IdentificationTypeDTO identificationTypeCode;
    private StateDTO stateCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getIdentification() {
        return identification;
    }

    public void setIdentification(Long identification) {
        this.identification = identification;
    }

    public IdentificationTypeDTO getIdentificationTypeCode() {
        return identificationTypeCode;
    }

    public void setIdentificationTypeCode(IdentificationTypeDTO identificationTypeCode) {
        this.identificationTypeCode = identificationTypeCode;
    }

    public StateDTO getStateCode() {
        return stateCode;
    }

    public void setStateCode(StateDTO stateCode) {
        this.stateCode = stateCode;
    }
}
