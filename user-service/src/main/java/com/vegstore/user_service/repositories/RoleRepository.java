package com.vegstore.user_service.repositories;

import com.vegstore.user_service.entities.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<UserRole,Integer> {

    @Query(value = "SELECT * FROM roles WHERE name = :name", nativeQuery = true)
    Optional<UserRole> getRoleByName(@Param("name") String name);
}
