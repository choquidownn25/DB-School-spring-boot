package com.proyecto.account.controller;

import com.itextpdf.text.DocumentException;
import com.proyecto.account.model.Departamento;
import com.proyecto.account.model.Empleado;
import com.proyecto.account.service.DepartamentoService;
import com.proyecto.account.service.EmpleadoService;
import com.proyecto.account.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.context.ApplicationContext;
@Controller
@RequestMapping("/empleado")
public class EmpleadoController {
    @Autowired
    private EmpleadoService empleadoService;
    @Value("${logoImgPath}")
    private String logoImgPath;
    @Value("${logoImgScale}")
    private Float[] logoImgScale;
    @Value("${reportFileName}")
    private String reportFileName;
    @Autowired
    private DepartamentoService departamentoService;
    private String add_edit_template = "/admin/empleado/add-edit-empleado";
    private String list_template = "/admin/empleado/list-empleado";
    private String list_redirect = "redirect:/empleado/list";
    private PDFGenerator pdfGenerator = new PDFGenerator();

    @GetMapping("/add")
    public String addEmpleado(Empleado empleado, Model model) {
        model.addAttribute("empleado", empleado);
        List<Departamento> departamento = departamentoService.getAllDepartamento();
        model.addAttribute("departamento", departamento);
        return add_edit_template;
    }

    @GetMapping("/edit/{id}")
    public String editEmpleado(@PathVariable("id") long id, Model model) throws Exception {
        Empleado empleado = empleadoService.getById(id);
        model.addAttribute("empleado", empleado);

        List<Departamento> departamento = departamentoService.getAllDepartamento();
        model.addAttribute("departamento", departamento);
        return add_edit_template;
    }

    @PostMapping("/save")
    public String saveEmpleado(@Valid @ModelAttribute("empleado") Empleado empleado, BindingResult result, Model model) throws Exception {
        model.addAttribute("empleado", empleado);
        List<Departamento> departamentos = departamentoService.getAllDepartamento();
        model.addAttribute("departamentos", departamentos);
        if (result.hasErrors()) {
            return add_edit_template;
        }
        empleadoService.save(empleado);
        return list_redirect + "?success";
    }

    @GetMapping("/list")
    public String listEmpleado(Model model) throws Exception {
        model.addAttribute("logoImgPath", logoImgPath);
        model.addAttribute("logoImgScale", logoImgScale);
        model.addAttribute("reportFileName", reportFileName);
        List<Departamento> departamento = departamentoService.getAllDepartamento();
        model.addAttribute("departamento", departamento);
        List<Empleado> listEmpleado = empleadoService.findAll();
        model.addAttribute("listEmpleado", listEmpleado);
        return list_template;
    }

    @GetMapping("/delete/{id}")
    public String deleteEmpleado(@PathVariable("id") long id, Model model) throws Exception {
        empleadoService.delete(id);
        return list_redirect + "?deleted";
    }

    public void reportAllEmpleado() {

    }

    @GetMapping("/report")
    public ResponseEntity<InputStreamResource> customerReport(Model model) throws Exception {
        model.addAttribute("logoImgPath", logoImgPath);
        model.addAttribute("logoImgScale", logoImgScale);
        model.addAttribute("reportFileName", reportFileName);
        List<Empleado> empleado = (List<Empleado>) empleadoService.findAll();

        ByteArrayInputStream bis = pdfGenerator.generatePdfReport(empleado, logoImgPath,  logoImgScale, reportFileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=customers.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}