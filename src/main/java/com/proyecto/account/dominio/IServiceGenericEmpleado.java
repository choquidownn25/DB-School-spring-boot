package com.proyecto.account.dominio;

import com.proyecto.account.model.Empleado;

import java.util.List;

public interface IServiceGenericEmpleado<T extends Empleado>  {
    List<T> findAll() throws Exception;
    T save(T entity) throws Exception;
    T delete(Long id) throws Exception;
    T getById(Long id) throws Exception;
}
