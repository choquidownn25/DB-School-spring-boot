package com.proyecto.account.repository;

import com.proyecto.account.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IDepartamentoRepository extends JpaRepository<Departamento, Long> {
}
