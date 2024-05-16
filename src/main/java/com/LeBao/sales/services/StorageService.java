package com.LeBao.sales.services;


import com.LeBao.sales.entities.Product;
import com.LeBao.sales.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final ProductRepository productRepository;

    public String generateNewFileName(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return UUID.randomUUID() + "." + extension;
    }

    public byte[] getImage(String fileName) throws IOException {
        String uploadDir = "upload/images/";
        String filePath = uploadDir + fileName;
        return Files.readAllBytes(new File(filePath).toPath());
    }

    public List<String> upload(MultipartFile[] files) throws IOException {
        List<String> paths = new ArrayList<>();
        try {
            if (!Arrays.stream(files).toList().isEmpty()) {

                String uploadDir = "upload/images";
                Path uploadPath = Paths.get(uploadDir);
                if(!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                for (MultipartFile file:files) {
                    InputStream inputStream = file.getInputStream();
                    String fileName = generateNewFileName(file);
                    Path filePath = uploadPath.resolve(fileName);
                    Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
                    paths.add(fileName);
                }
            }
            return paths;
        }catch (IOException ioException) {
            throw new IOException("Some thing went wrong");
        }
    }

    public byte[] downloadImageFromFileSystem(Long productId) throws IOException {
        Product dbImageData = productRepository.findById(productId).get();
        String filePath = dbImageData.getImages().get(0);
        return Files.readAllBytes(new File(filePath).toPath());
    }

}
