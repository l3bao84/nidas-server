package com.LeBao.sales.controllers;

import com.LeBao.sales.entities.User;
import com.LeBao.sales.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customerInfor")
@RequiredArgsConstructor
public class CustomerInforController {

    @Autowired
    private UserService userService;

    @PostMapping("/updateCustomerInfor/{customerId}")
    public ResponseEntity<String> updateCustomerInfor(@PathVariable Long customerId, @RequestBody User user) {
        String response = userService.updateCustomerInfor(customerId, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(userService.getUserById(userId));
    }
}
