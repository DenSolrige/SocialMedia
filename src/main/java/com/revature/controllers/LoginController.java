package com.revature.controllers;

import com.revature.dtos.JwtInfo;
import com.revature.dtos.LoginCredentials;
import com.revature.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping
    public JwtInfo login(@RequestBody LoginCredentials loginCredentials){
        return loginService.authenticateUser(loginCredentials);
    }
}
