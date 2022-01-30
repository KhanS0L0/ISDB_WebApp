package com.example.controller;

import com.example.dto.AuthRegDTO.RegRequestDTO;
import com.example.exceptions.UserAlreadyExistsException;
import com.example.service.interfaces.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity signUp(@RequestBody RegRequestDTO requestDTO){
        try{
            registrationService.signUp(requestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (UserAlreadyExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
