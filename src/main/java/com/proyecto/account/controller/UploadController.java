package com.proyecto.account.controller;

import com.proyecto.account.model.Departamento;
import com.proyecto.account.model.Empleado;
import com.proyecto.account.service.StorageService;
import com.proyecto.account.util.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller

public class UploadController {
    private String uploadSuccess = "/admin/upload/upload-success";
    private String uploadFailure = "/admin/upload/upload-failure";
    private String list_template = "/admin/upload/index-upload";
    @Autowired
    private StorageService storageService;
    //Anotacion para cargar la imagen
    @RequestMapping(value = "/doUpload", method = RequestMethod.POST,
            consumes = {"multipart/form-data"})
    public String Upload(@RequestParam MultipartFile file){

        try {
            storageService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uploadSuccess;
    }
    //Exception
    @ExceptionHandler(StorageException.class)
    public String HandleStorageFileNotFound(StorageException e){
        return uploadFailure;
    }
    @RequestMapping("/upload")
    public String ListUpload(Model model) throws Exception {

        return list_template;
    }
}
