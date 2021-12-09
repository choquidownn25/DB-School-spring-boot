package com.proyecto.account.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "role")
public class Role {
    public Role() {

    }
    public Role(String name) {
        super();
        this.name =name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Column(name="name")
    private String name;
}
