package com.example.service.interfaces;

import com.example.dto.AuthRegDTO.AuthRequestDTO;

import java.util.Map;

public interface AuthenticationService {
    Map<Object, Object> signIn(AuthRequestDTO requestDTO);
}
