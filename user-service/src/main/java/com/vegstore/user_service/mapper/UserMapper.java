package com.vegstore.user_service.mapper;

import com.vegstore.user_service.dao.UserDTO;
import com.vegstore.user_service.dao.UserRegisterDTO;
import com.vegstore.user_service.entities.User;
import com.vegstore.user_service.enums.Role;

public class UserMapper {

    public static UserDTO toDTO(User user){
        UserDTO userDTO= new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        return userDTO;

    };

    public static User fromDTO(UserRegisterDTO dto) {
        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setPassword(dto.getPassword());
       // fallback if null
        user.setVerified(false); // default
        user.setBlocked(false);  // default
        return user;
    }

}
