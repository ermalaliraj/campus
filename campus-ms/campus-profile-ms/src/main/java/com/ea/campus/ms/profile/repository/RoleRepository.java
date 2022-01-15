package com.ea.campus.ms.profile.repository;

import com.ea.campus.ms.profile.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String>, JpaSpecificationExecutor<Role> {

    Optional<Role> findByName(String name);

    void deleteByName(String name);
}
