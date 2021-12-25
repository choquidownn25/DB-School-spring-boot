package com.proyecto.account.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name="empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_departamento")
    @NotNull(message = "Select Product type!")
    private Integer id_departamento;

    /*@NotEmpty(message = "nif can't be empty!")
    @Column(name = "nif")
    private String nif;*/

    @NotEmpty(message = "Name can't be empty!")
    @Column(name = "nombre")
    private String nombre;

    @NotEmpty(message = "apellido can't be empty!")
    @Column(name = "apellido")
    private String apellido;
    @NotEmpty(message = "ciudad can't be empty!")
    @Column(name = "ciudad")
    private String ciudad;
    @NotEmpty(message = "direccion can't be empty!")
    @Column(name = "direccion")
    private String direccion;
    @NotEmpty(message = "telefono can't be empty!")
    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_nacimiento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_nacimiento;



}
