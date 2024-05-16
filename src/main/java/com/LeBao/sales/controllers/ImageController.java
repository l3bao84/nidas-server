package com.LeBao.sales.controllers;

import com.LeBao.sales.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/img")
@RequiredArgsConstructor
public class ImageController {

    private final StorageService storageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<?> getImage(@PathVariable String fileName) throws IOException {
        byte[] imageData = storageService.getImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }
}
