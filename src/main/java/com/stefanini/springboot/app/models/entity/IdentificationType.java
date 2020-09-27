package com.stefanini.springboot.app.models.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class IdentificationType {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length=20)
    private String name;

    @Column(name="create_day")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDay;

    @Column(name="create_user", length=20)
    private String createUser;

    @Column(name="modification_day")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDay;

    @Column(name="modification_user", length=20)
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
