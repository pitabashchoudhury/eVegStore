package com.vegstore.user_service.service.interfaces;

import com.vegstore.user_service.dao.UserDTO;
import com.vegstore.user_service.dao.UserRegisterDTO;
import com.vegstore.user_service.entities.User;
import com.vegstore.user_service.enums.Role;
import com.vegstore.user_service.exception.ResourceNotFoundException;
import com.vegstore.user_service.exception.UserCreationException;
import com.vegstore.user_service.mapper.UserMapper;
import com.vegstore.user_service.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Service
public  class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Override
    public Optional<UserDTO> fetchUserByEmail(String email) {

       final Optional <User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            // Throwing 404 Not Found if user doesn't exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }

        User userData= user.get();

        // Assuming you have a method to convert Entity to DTO
        UserDTO userDTO = user.map(UserMapper::toDTO).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return Optional.of(userDTO);
    }

    @Override
    public UserDTO createUser(UserRegisterDTO userDTO) {

        try {
            User user = UserMapper.fromDTO(userDTO);
            user.setRole( Role.CUSTOMER);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            user.setCreatedAt(now);
            user.setUpdatedAt(now);
            User saved = userRepository.save(user);
            return UserMapper.toDTO(saved);
        } catch (Exception e) {
            throw new UserCreationException("Failed to create user", e);
        }
    }
}
