package com.ea.campus.ms.profile.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto extends BaseDto {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled;

    private List<RoleDto> roles = new ArrayList<>();
}
