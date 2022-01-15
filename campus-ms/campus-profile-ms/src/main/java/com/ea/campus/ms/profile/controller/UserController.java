package com.ea.campus.ms.profile.controller;

import com.ea.campus.ms.profile.dto.PageDto;
import com.ea.campus.ms.profile.dto.RoleDto;
import com.ea.campus.ms.profile.dto.UserDto;
import com.ea.campus.ms.profile.dto.mapper.RoleDtoMapper;
import com.ea.campus.ms.profile.dto.mapper.UserDtoMapper;
import com.ea.campus.ms.profile.model.Role;
import com.ea.campus.ms.profile.model.User;
import com.ea.campus.ms.profile.queryparam.UserQueryParam;
import com.ea.campus.ms.profile.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDtoMapper userDtoMapper;
    @Autowired
    private RoleDtoMapper roleDtoMapper;

    @GetMapping(value = "/{id}", produces = "application/json")
    public UserDto findById(@PathVariable("id") String id) {
        User user = userService.findById(id).orElse(null);
        return userDtoMapper.toDto(user);
    }

    @GetMapping(produces = "application/json")
    public PageDto<UserDto> findAll(UserQueryParam params, Pageable pageable) {
        Page<User> page = userService.findAll(params, pageable);
        return userDtoMapper.toPageDto(page);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public UserDto insert(@RequestBody UserDto dto) {
        User user = userDtoMapper.toEntity(dto);
        user = userService.insert(user);
        return userDtoMapper.toDto(user);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public UserDto update(@RequestBody UserDto dto) {
        User user = userDtoMapper.toEntity(dto);
        user = userService.update(user);
        return userDtoMapper.toDto(user);
    }

    @PutMapping("/{id}")
    public UserDto updateUserRoles(@RequestBody List<RoleDto> roleDtos, @PathVariable("id") String id) {
        List<Role> roles = roleDtoMapper.toEntity(roleDtos);
        User user = userService.updateUserRoles(roles, id);
        return userDtoMapper.toDto(user);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable("id") String id) {
        userService.delete(id);
    }

    @PutMapping(value = "/{id}/{enabled}")
    public void enableUser(@PathVariable("id") String id, @PathVariable("enabled") boolean enabled) {
        userService.enableUser(id, enabled);
    }

}

