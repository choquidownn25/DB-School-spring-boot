package com.proyecto.account.service;


import com.proyecto.account.util.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    @Value("${upload.path}")
    private String path;

    public void uploadFile(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            Path paths = Paths.get(path);
            if (!Files.exists(paths)) {

                Files.createDirectory(paths);
                System.out.println("Directory created");
                try {
                    var fileName = file.getOriginalFilename();
                    var is = file.getInputStream();
                    Files.copy(is, Paths.get(path + fileName),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    var msg = String.format("Failed to store file %f", file.getName());
                    throw new StorageException(msg, e);
                }
            } else {
                throw new StorageException("Failed to store empty file");
            }

        }

        try {
            var fileName = file.getOriginalFilename();
            var is = file.getInputStream();

            Files.copy(is, Paths.get(path + fileName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

            var msg = String.format("Failed to store file %f", file.getName());

            throw new StorageException(msg, e);
        }
    }
}
