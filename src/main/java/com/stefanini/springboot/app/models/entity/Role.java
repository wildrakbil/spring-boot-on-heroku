package com.stefanini.springboot.app.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", length=20)
    private String name;

    @Column(name="permits", length=20)
    private String permits;

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

    public String getPermits() {
        return permits;
    }

    public void setPermits(String permits) {
        this.permits = permits;
    }
}
