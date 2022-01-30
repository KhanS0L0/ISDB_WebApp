package com.example.service.implementations;

import com.example.entity.users.Role;
import com.example.entity.enums.Status;
import com.example.entity.users.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.repository.WorkerRepository;
import com.example.service.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User addUser(User newUser) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoles(userRoles);
        newUser.setStatus(Status.ACTIVE);

        User registeredUser = userRepository.save(newUser);
        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = userRepository.findAll();
        log.info("IN getAll - {} users found", userList.size());
        return userList;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null){
            log.warn("IN findByUsername - no user found by username: {}", username);
        }
        log.info("IN findByUsername - user: {} found by username: {}", user, username);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            log.warn("IN findById - no user found by id: {}", id);
        }
        log.info("IN findById - user: {} found by id: {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted", id);
    }
}
