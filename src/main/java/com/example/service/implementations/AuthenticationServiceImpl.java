package com.example.service.implementations;

import com.example.dto.AuthRegDTO.AuthRequestDTO;
import com.example.entity.users.User;
import com.example.security.jwt.util.JwtTokenProvider;
import com.example.service.interfaces.AuthenticationService;
import com.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    public Map<Object, Object> signIn(AuthRequestDTO requestDTO) {
        try{
            String username = requestDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.getPassword()));

            User user = userService.findByUsername(username);
            if(user == null){
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            Long workerId = user.getWorker().getId();
            Long userId = user.getId();
            String token = jwtTokenProvider.createToken(username, user.getRoles(), workerId, userId);

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return response;

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
