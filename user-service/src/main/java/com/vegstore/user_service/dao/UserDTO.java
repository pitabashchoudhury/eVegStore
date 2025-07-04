package com.vegstore.user_service.dao;

import com.vegstore.user_service.enums.Role;
import lombok.*;

@Data
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private  String fullName;

    private  String email;

    private  String phoneNumber;

    private  Role role;
}
