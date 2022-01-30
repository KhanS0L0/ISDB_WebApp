package com.example.controller;

import com.example.dto.AuthRegDTO.AuthRequestDTO;
import com.example.service.interfaces.AuthenticationService;
import com.example.service.interfaces.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/login")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, RegistrationService registrationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity signIn(@RequestBody AuthRequestDTO requestDTO){
        try{
            Map<Object, Object> response = authenticationService.signIn(requestDTO);
            return ResponseEntity.ok(response);
        }catch(AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
