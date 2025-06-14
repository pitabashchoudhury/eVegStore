package com.vegstore.user_service.service.interfaces;


import com.vegstore.user_service.dao.UserDTO;
import com.vegstore.user_service.dao.UserRegisterDTO;

import java.util.Optional;

public interface UserService {

    public Optional<UserDTO> fetchUserByEmail(String email);
    public UserDTO createUser(UserRegisterDTO userDTO);
}
