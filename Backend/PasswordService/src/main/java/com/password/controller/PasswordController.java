package com.password.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.password.service.PasswordStrengthService;

@RestController
@RequestMapping("/password-strength")
public class PasswordController {

    @Autowired
    private PasswordStrengthService passwordStrengthService;

    @PostMapping("/check")
    public String checkPasswordStrength(@RequestBody String password) {
        return passwordStrengthService.evaluatePasswordStrength(password);
    }
}
