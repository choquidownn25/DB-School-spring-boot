package com.proyecto.account.bean.implementation;


import com.proyecto.account.bean.MyBean;

public class MyBeanImpl implements MyBean {

    @Override
    public String hello() {
        return "Hello from my BeanImpl";
    }
}
