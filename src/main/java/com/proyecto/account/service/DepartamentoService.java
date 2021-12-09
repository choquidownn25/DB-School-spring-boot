package com.proyecto.account.service;

import com.proyecto.account.dominio.IDepartamento;
import com.proyecto.account.model.Departamento;
import com.proyecto.account.repository.IDepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class DepartamentoService implements IDepartamento {
    @Autowired
    private IDepartamentoRepository repository;

    @Override
    public List<Departamento> getAllDepartamento() {
        try {
            return repository.findAll();
        }catch (Exception ex){
            throw new IllegalStateException("Error " + ex.getMessage());
        }
    }

    @Override
    public Departamento saveDepartamento(Departamento departamento) {
        if(departamento != null)
            return repository.save(departamento);
        else
            throw new EntityNotFoundException("Error varificar datos" + departamento.toString());
    }

    @Override
    public Departamento getDepartamentoById(long id) {
        //Optional es un objeto contenedor que puede o no contener un valor no nulo.
        // Si el valor es presente, isPresent() devuelve true y get() retorna el valor.
        Optional<Departamento> optional = repository.findById(id);
        Departamento departamento = null;
        if (optional.isPresent()) {
            departamento = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return departamento;
    }

    @Override
    public Departamento deleteDepartamentoById(long id) {
        Optional<Departamento> optional = repository.findById(id);
        Departamento departamento = null;
        if (optional.isPresent()) {
            departamento = optional.get();
            repository.deleteById(id);
            return departamento;
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
    }
    //lista todo

}
