package com.ea.campus.ms.profile.controller;

import com.ea.campus.ms.profile.dto.RoleDto;
import com.ea.campus.ms.profile.dto.mapper.RoleDtoMapper;
import com.ea.campus.ms.profile.model.Role;
import com.ea.campus.ms.profile.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
public class RoleController {

    @Autowired
    private RoleService service;
    @Autowired
    private RoleDtoMapper mapper;

    @GetMapping
    public List<RoleDto> findAll() {
        List<Role> roles = service.findAll();
        return mapper.toDto(roles);
    }

    @GetMapping(value = "/{id}")
    public RoleDto findById(@PathVariable("id") String id) {
        Role role = service.findById(id).orElse(null);
        return mapper.toDto(role);
    }

    @GetMapping(value = "/name/{name}")
    public RoleDto findByName(@PathVariable("name") String name) {
        Role role = service.findByName(name).orElse(null);
        return mapper.toDto(role);
    }

    @PostMapping
    public RoleDto insert(@RequestBody RoleDto dto) {
        Role role = mapper.toEntity(dto);
        role = service.insert(role);
        return mapper.toDto(role);
    }

    @DeleteMapping(value = "/{name}")
    public void deleteByName(@PathVariable("name") String name) {
        service.deleteByName(name);
    }
}

