package com.proyecto.account.service;

import com.proyecto.account.dominio.IServiceGenericEmpleado;
import com.proyecto.account.model.Departamento;
import com.proyecto.account.model.Empleado;
import com.proyecto.account.repository.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpleadoService implements IServiceGenericEmpleado {
    @Autowired
    private IEmpleadoRepository repository;
    @Override
    public List findAll() throws Exception {
        try {
            return repository.findAll();
        }catch (Exception ex){
            throw new IllegalStateException("Error " + ex.getMessage());
        }
    }

    @Override
    public Empleado save(Empleado entity) throws Exception {
        if(entity != null)
            return repository.save(entity);
        else
            throw new EntityNotFoundException("Error varificar datos" + entity.toString());
    }

    @Override
    public Empleado delete(Long id) throws Exception {
        //Optional es un objeto contenedor que puede o no contener un valor no nulo.
        // Si el valor es presente, isPresent() devuelve true y get() retorna el valor.
        Optional<Empleado> optional = repository.findById(id);
        Empleado empleado = null;
        if (optional.isPresent()) {
            empleado = optional.get();
            repository.deleteById(id);
            return empleado;
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }

    }

    @Override
    public Empleado getById(Long id) throws Exception {
        //Optional es un objeto contenedor que puede o no contener un valor no nulo.
        // Si el valor es presente, isPresent() devuelve true y get() retorna el valor.
        Optional<Empleado> optional = repository.findById(id);
        Empleado empleado = null;
        if (optional.isPresent()) {
            empleado = optional.get();
        } else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return empleado;
    }
}
