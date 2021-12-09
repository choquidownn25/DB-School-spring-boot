package com.proyecto.account.restcontroller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonAutoDetect
public class MorisArea implements Serializable {
    private static final long serialVersionUID = 4992047206653043217L;
    private String y;
    private Number item1;
    private Number item2;
    public MorisArea(String y, Number item1, Number item2){
        this.y=y;
        this.item1=item1;
        this.item2=item2;
    }
}
