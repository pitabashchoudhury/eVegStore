package com.vegstore.user_service.service;

import com.vegstore.user_service.entities.User;
import com.vegstore.user_service.entities.UserRole;
import com.vegstore.user_service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public CustomUserService() {
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

            Optional<User> user=  userRepository.findByFullName(userName);
            if (user.isPresent()){
                return new org.springframework.security.core.userdetails.User(
                        user.get().getFullName(),
                        user.get().getPassword(),

                        user.get().getRoles().stream().map(e->new SimpleGrantedAuthority(e.getName())).collect(Collectors.toSet())
                );
            }

            throw  new UsernameNotFoundException("user Not Found");
    }
}
