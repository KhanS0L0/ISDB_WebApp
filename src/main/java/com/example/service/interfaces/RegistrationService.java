package com.example.service.interfaces;

import com.example.dto.AuthRegDTO.RegRequestDTO;
import com.example.exceptions.UserAlreadyExistsException;

public interface RegistrationService {
    void signUp(RegRequestDTO requestDTO) throws UserAlreadyExistsException;
}
