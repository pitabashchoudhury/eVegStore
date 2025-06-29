package com.vegstore.user_service.service;


import com.vegstore.user_service.dao.LogInRequestDTO;
import com.vegstore.user_service.dao.LoginResponseDTO;
import com.vegstore.user_service.dao.UserDTO;
import com.vegstore.user_service.dao.UserRegisterDTO;
import com.vegstore.user_service.entities.User;
import com.vegstore.user_service.entities.UserRole;
import com.vegstore.user_service.enums.Role;
import com.vegstore.user_service.exception.EmailAlreadyExistsException;
import com.vegstore.user_service.exception.ResourceNotFoundException;
import com.vegstore.user_service.exception.UserCreationException;
import com.vegstore.user_service.mapper.UserMapper;
import com.vegstore.user_service.repositories.RoleRepository;
import com.vegstore.user_service.repositories.UserRepository;
import com.vegstore.user_service.service.interfaces.UserService;
import com.vegstore.user_service.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.sql.SQLException;
import java.sql.Timestamp;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public  class UserServiceImpl implements UserService {
    private final JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    private  final CustomUserService customUserService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository , PasswordEncoder passwordEncoder, CustomUserService customUserService, JwtUtil jwtUtil) {
        this.userRepository=userRepository;
        this.roleRepository= roleRepository;
        this.passwordEncoder=passwordEncoder;
        this.customUserService=customUserService;
        this.jwtUtil=jwtUtil;




    }


    @Override
    public Optional<UserDTO> fetchUserByEmail(String email) {


        final Optional <User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            // Throwing 404 Not Found if user doesn't exist
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }


        UserDTO userDTO = user.map(UserMapper::toDTO).orElseThrow(() -> new ResourceNotFoundException("User not found"));


        return Optional.of(userDTO);
    }


    @Override
    public UserDTO createUser(UserRegisterDTO userDTO)  {


        try {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                throw new EmailAlreadyExistsException("Email already exists: " + userDTO.getEmail());
            }


            final Optional<UserRole> userRole=  roleRepository.getRoleByName(userDTO.getRole().name());


            if(userRole.isEmpty()){
                throw new ResourceNotFoundException("Role does not exist: " + userDTO.getRole().name());
            }


            User user = UserMapper.fromDTO(userDTO);
            user.getRoles().add(userRole.get());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            Timestamp now = new Timestamp(System.currentTimeMillis());
            user.setCreatedAt(now);
            user.setUpdatedAt(now);
            User saved = userRepository.save(user);
            return UserMapper.toDTO(saved);
        }
        catch (EmailAlreadyExistsException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new UserCreationException("Failed to create user", e);
        }


    }


    @Override
    public Optional<UserDTO> fetchUserByPhone(String phone) {


        try {


            final Optional<User> user = userRepository.findByPhoneNumber(phone);


            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with phone: " + phone);
            }


            User userData = user.get();


            UserDTO userDTO = user.map(UserMapper::toDTO).orElseThrow(() -> new ResourceNotFoundException("User not found"));


            return Optional.of(userDTO);
        }catch (ResponseStatusException | ResourceNotFoundException e){


            throw e;




        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<UserDTO> fetchAllUsers() {


        try {
            List<User> users = userRepository.findAll();


            return users.stream()
                    .map(user -> {
                        try {
                            return UserMapper.toDTO(user);
                        } catch (RuntimeException e) {


                            return null; // or you can throw again or use Optional.empty()
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());


        }catch (Exception sqlException){
            throw  sqlException;
        }
    }


    @Override
    public LoginResponseDTO login(LogInRequestDTO logInRequestDTO) {


        try{


            Authentication auth= new UsernamePasswordAuthenticationToken(logInRequestDTO.getUsername(), logInRequestDTO.getPassword());


            authenticationManager.authenticate(auth);


            final Optional<User> user = userRepository.findByFullName(logInRequestDTO.getUsername());

            if (user.isEmpty()){
                throw new ResourceNotFoundException("Email does not exist: " + logInRequestDTO.getUsername());
            }
            final String jwt = jwtUtil.generateToken(user.get());
            LoginResponseDTO loginResponseDTO= new LoginResponseDTO();

            loginResponseDTO.setFullName(user.get().getFullName());
            loginResponseDTO.setPhoneNumber(user.get().getPhoneNumber());
            loginResponseDTO.setEmail(user.get().getEmail());
            loginResponseDTO.setToken(jwt);
            return loginResponseDTO;
        } catch (ResourceNotFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
