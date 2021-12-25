package com.proyecto.account.controller;
import com.proyecto.account.repository.StorageService;
import com.proyecto.account.service.FileSystemStorageService;
import com.proyecto.account.util.exception.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class FileUploadController {
    private String list_template = "/admin/upload/index-upload";
    private final StorageService storageService;
    private static String UPLOAD_FOLDER = "C://upload//";
    private final String UPLOAD_DIR = "C://upload//";
    private FileSystemStorageService fileSystemStorageService;
    @Value("${pathPhoto}")
    private String pathPhoto;
    @Autowired
    public FileUploadController(StorageService storageService, FileSystemStorageService fileSystemStorageService) {
        this.storageService = storageService;
        this.fileSystemStorageService=fileSystemStorageService;
    }


    @GetMapping("/index")
    public String listEmpleado(Model model) throws Exception {

        return list_template;
    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/";
        }

        fileSystemStorageService.store(file);
        String datoImage = pathPhoto;
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            //

            Path paths = Paths.get(datoImage );
            Path path = Paths.get(pathPhoto + fileName);
            if (!Files.exists(paths)) {
                System.out.println("Directory created :" + paths);

                Files.createDirectories(Paths.get(datoImage ));
                System.out.println("Directory created");
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            } else {

                System.out.println("Directory already exists");
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            e.printStackTrace();


        }

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');

        return "redirect:/";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}