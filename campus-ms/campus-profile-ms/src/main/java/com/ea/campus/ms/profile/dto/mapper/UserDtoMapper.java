package com.ea.campus.ms.profile.dto.mapper;

import com.ea.campus.ms.profile.dto.PageDto;
import com.ea.campus.ms.profile.dto.RoleDto;
import com.ea.campus.ms.profile.dto.UserDto;
import com.ea.campus.ms.profile.model.Role;
import com.ea.campus.ms.profile.model.User;
import org.hibernate.Hibernate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(config = DtoMapperConfiguration.class, uses = {RoleDtoMapper.class})
public abstract class UserDtoMapper {

    @Autowired
    protected RoleDtoMapper roleDtoMapper;

    @Mapping(target = "roles", expression = "java(toRoleDto(user))")
    public abstract UserDto toDto(User user);

    public abstract List<UserDto> toDto(List<User> users);

    public PageDto toPageDto(Page<User> page) {
        final List<UserDto> userDtos = toDto(page.getContent());
        return new PageDto(userDtos, page);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    public abstract void updateEntity(User newUser, @MappingTarget User oldEntity);

    public abstract User toEntity(UserDto dto);

    public List<RoleDto> toRoleDto(User user) {
        final List<Role> userRoles = user.getRoles();
        if (userRoles == null) {
            return null;
        }
        return roleDtoMapper.toDto(Hibernate.unproxy(userRoles, Hibernate.getClass(userRoles)));
    }

}
