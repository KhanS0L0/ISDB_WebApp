package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

//    private final UserService userService;
//
//    @Autowired
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id){
//        User user = userService.findById(id);
//        if(user == null){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//        UserDTO result = UserDTO.fromUser(user);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
}
