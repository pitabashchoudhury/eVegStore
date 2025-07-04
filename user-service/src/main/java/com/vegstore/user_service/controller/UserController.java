package com.vegstore.user_service.controller;

import com.vegstore.user_service.dao.UserDTO;
import com.vegstore.user_service.dao.UserRegisterDTO;
import com.vegstore.user_service.entities.User;
import com.vegstore.user_service.exception.ResourceNotFoundException;
import com.vegstore.user_service.repositories.UserRepository;
import com.vegstore.user_service.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user" , produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserDTO>> getUser(@PathVariable String mail) {
        Optional<UserDTO>user= userService.fetchUserByEmail(mail);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/email")
    public ResponseEntity<UserDTO> searchUserByEmail(@RequestParam String mail) {
        UserDTO user = userService.fetchUserByEmail(mail)
                .orElseThrow(() -> new ResourceNotFoundException(mail));
        return ResponseEntity.ok(user);
    }


    @PostMapping("/register-user")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserDTO user = userService.createUser(userRegisterDTO);
        return ResponseEntity.ok(user);
    }



}
