package com.vegstore.user_service.controller;

import com.vegstore.user_service.dao.LogInRequestDTO;
import com.vegstore.user_service.dao.LoginResponseDTO;
import com.vegstore.user_service.dao.UserDTO;
import com.vegstore.user_service.dao.UserRegisterDTO;
import com.vegstore.user_service.entities.User;
import com.vegstore.user_service.exception.ResourceNotFoundException;
import com.vegstore.user_service.repositories.UserRepository;
import com.vegstore.user_service.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/user" , produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user= userService.fetchUserById(id);
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


    @PostMapping("/phone")
    public ResponseEntity<UserDTO> searchUserByPhone(@RequestParam String phoneNumber) {
        Optional<UserDTO> user = userService.fetchUserByPhone(phoneNumber);
        return ResponseEntity.ok(user.get());
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/fetch-users")
    public ResponseEntity<?> fetchUsers() {
        List<UserDTO> user = userService.fetchAllUsers();
        return ResponseEntity.ok(user);
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> login(@RequestBody LogInRequestDTO logInRequestDTO) {
        LoginResponseDTO user = userService.login(logInRequestDTO);
        return ResponseEntity.ok(user);
    }



    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){

        String message= userService.deleteUserByID(id);

        return  ResponseEntity.ok(message);
    }


}
