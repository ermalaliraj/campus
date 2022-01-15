package com.ea.campus.ms.profile.service;

import com.ea.campus.ms.profile.exception.EntityNotFoundException;
import com.ea.campus.ms.profile.model.Role;
import com.ea.campus.ms.profile.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public Optional<Role> findById(String id) {
        return repository.findById(id);
    }

    public List<Role> findAll() {
        return repository.findAll();
    }

    public Optional<Role> findByName(String name) {
        return repository.findByName(name);
    }

    public Role insert(Role role) {
        return repository.save(role);
    }

    public void deleteByName(String name) {
        Role role = findByName(name).orElseThrow(() -> new EntityNotFoundException(Role.class, "name", name));
        repository.delete(role);
    }
}
