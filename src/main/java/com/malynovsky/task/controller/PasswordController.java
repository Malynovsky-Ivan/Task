package com.malynovsky.task.controller;

import com.malynovsky.task.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class PasswordController {

    private final PasswordService passwordService;

    @Autowired
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @GetMapping("/{email}")
    public String getOTP(@PathVariable(name = "email") String email) {
        return passwordService.getPasswordByEmail(email);
    }

    @PostMapping ("/token")
    public String generateToken(@RequestBody String otp) {
        passwordService.generateToken(otp);
        return "Token was sent to your email!";
    }
}
