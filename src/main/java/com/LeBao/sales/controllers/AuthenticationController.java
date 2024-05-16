package com.LeBao.sales.controllers;

import com.LeBao.sales.exceptions.AuthenticationFailedException;
import com.LeBao.sales.requests.AuthenticationRequest;
import com.LeBao.sales.responses.AuthenticationResponse;
import com.LeBao.sales.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse response = new AuthenticationResponse(authenticationService.authenticate(request));
        return ResponseEntity.ok().body(response);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<?> handleAuthenticationFailedException(AuthenticationFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


}
