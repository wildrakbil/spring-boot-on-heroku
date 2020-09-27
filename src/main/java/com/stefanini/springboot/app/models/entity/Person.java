package com.stefanini.springboot.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", length = 100)
    private String firstname;
    @Column(name = "last_name", length = 100)
    private String lastname;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(name = "user_name", unique = true, length = 20)
    private String username;
    @Column(name = "password", length = 60)
    private String password;
    @Column(name = "identification")
    private Long identification;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "identification_type_code")
    private IdentificationType identificationTypeCode;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "state_code")
    private State stateCode;

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

    public IdentificationType getIdentificationTypeCode() {
        return identificationTypeCode;
    }

    public void setIdentificationTypeCode(IdentificationType identificationTypeCode) {
        this.identificationTypeCode = identificationTypeCode;
    }

    public State getStateCode() {
        return stateCode;
    }

    public void setStateCode(State stateCode) {
        this.stateCode = stateCode;
    }
}
