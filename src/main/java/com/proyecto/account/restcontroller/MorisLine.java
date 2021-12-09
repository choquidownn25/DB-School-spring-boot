package com.proyecto.account.restcontroller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.io.Serializable;
@Data
@JsonAutoDetect
public class MorisLine implements Serializable {
    private static final long serialVersionUID = 4992047206653043217L;
    private String y;
    private Number item1;
    public MorisLine(String y, Number item1) {
        this.y = y;
        this.item1 = item1;
    }
}
