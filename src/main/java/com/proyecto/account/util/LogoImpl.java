package com.proyecto.account.util;

public class LogoImpl implements ILogo{
    private String logo;


    public LogoImpl(String logo) {
        this.logo = logo;

    }
    @Override
    public String LogoImagen() {
        return logo;
    }
}
