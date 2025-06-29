package com.vegstore.user_service.service.interfaces;

import com.vegstore.user_service.dao.LogInRequestDTO;
import com.vegstore.user_service.dao.LoginResponseDTO;
import com.vegstore.user_service.dao.UserDTO;
import com.vegstore.user_service.dao.UserRegisterDTO;

import java.util.List;
import java.util.Optional;


public interface UserService {

    public Optional<UserDTO> fetchUserByEmail(String email);
    public UserDTO createUser(UserRegisterDTO userDTO);
    public Optional<UserDTO> fetchUserByPhone(String phone);
    public List<UserDTO> fetchAllUsers();

    public LoginResponseDTO login(LogInRequestDTO logInRequestDTO);
}
