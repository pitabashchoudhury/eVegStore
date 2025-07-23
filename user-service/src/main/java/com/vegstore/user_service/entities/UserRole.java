package com.vegstore.user_service.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class UserRole {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Integer isActive;

    public UserRole(Integer id, String name, Integer isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }

    public UserRole() {
    }
}
