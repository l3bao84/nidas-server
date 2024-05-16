package com.LeBao.sales.controllers;

import com.LeBao.sales.entities.User;
import com.LeBao.sales.requests.RegistrationRequest;
import com.LeBao.sales.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> customerRegister(@RequestBody RegistrationRequest request) {
        if(userService.customerRegister(request) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email address already in use.");
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(userService.customerRegister(request));
        }
    }
}
