package com.vegstore.user_service.dao;



import com.vegstore.user_service.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterDTO {

    private String fullName;

    private String email;

    private String phoneNumber;

    private String password;


}
