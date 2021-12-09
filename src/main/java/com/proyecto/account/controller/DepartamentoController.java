package com.proyecto.account.controller;

import com.proyecto.account.model.Departamento;
import com.proyecto.account.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/departamento")
public class DepartamentoController {
    @Autowired
    private DepartamentoService departamentoService;
    private String add_edit_template="/admin/departamento/add-edit-departamento";
    private String list_template="/admin/departamento/list-departamento";
    private String list_redirect="redirect:/departamento/list";
    //Agregar
    @GetMapping("/add")
    public String addDepartamento(Departamento departamento, Model model){
        model.addAttribute("departamento", departamento);
        return add_edit_template;
    }
    //Editar
    @GetMapping("/edit/{id}")
    public String editDepartamento(@PathVariable("id") int id, Model model){
        Departamento departamento = departamentoService.getDepartamentoById(id);

        model.addAttribute("departamento", departamento);

        return add_edit_template;
    }
    //Agrega datos en DB
    @PostMapping("/save")
    public String saveDepartamento(@Valid @ModelAttribute("producttype") Departamento departamento, BindingResult result, Model model){
        model.addAttribute("departamento", departamento);

        if(result.hasErrors()){
            return add_edit_template;
        }
        departamentoService.saveDepartamento(departamento);

        return list_redirect+"?success";
    }
    //Eliminar
    @GetMapping("/delete/{id}")
    public String deleteDepartamento(@PathVariable("id") int id, Model model) {
        departamentoService.deleteDepartamentoById(id);

        return list_redirect+"?deleted";
    }
    @GetMapping("/list")
    public String listDepartamento(Model model) {
        List<Departamento> listDepartamento = departamentoService.getAllDepartamento();
        model.addAttribute("listDepartamento", listDepartamento);

        return list_template;
    }
}
