package com.proyecto.account.dominio;

import com.proyecto.account.model.Departamento;

import java.util.List;
//Servicios para crud
public interface IDepartamento {
    List<Departamento> getAllDepartamento();
    Departamento saveDepartamento(Departamento departamento);
    Departamento getDepartamentoById(long id);
    Departamento deleteDepartamentoById(long id);
}
