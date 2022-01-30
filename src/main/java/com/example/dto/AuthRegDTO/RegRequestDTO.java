package com.example.dto.AuthRegDTO;

import lombok.Data;

import java.util.List;

@Data
public class RegRequestDTO {
    private Long userId;
    private Long workerId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String description;
    private List<String> roles;
    private boolean artist;
    private boolean screenwriter;
}
