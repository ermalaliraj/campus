package com.ea.campus.ms.profile.dto.mapper;

import com.ea.campus.ms.profile.dto.RoleDto;
import com.ea.campus.ms.profile.model.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = DtoMapperConfiguration.class)
public abstract class RoleDtoMapper {

    public abstract RoleDto toDto(Role entity);

    public abstract Role toEntity(RoleDto dto);

    public abstract List<Role> toEntity(List<RoleDto> dtos);

    public abstract List<RoleDto> toDto(List<Role> entities);

}
