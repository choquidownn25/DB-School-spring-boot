package com.proyecto.account.bean.implementation;


import com.proyecto.account.bean.MyBean;

public class MyBeanTwoImpl implements MyBean {

    private String name;


    public MyBeanTwoImpl(String name) {
        this.name = name;

    }

    @Override
    public String hello() {
        return  name;
    }
}
