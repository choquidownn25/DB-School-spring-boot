package com.proyecto.account.restcontroller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonAutoDetect
public class MorisDonut implements Serializable {
    private static final long serialVersionUID = 4992047206653043217L;
    private String label;
    private Number value;
    public MorisDonut(String label, Number value) {
        this.label = label;
        this.value = value;
    }
}
