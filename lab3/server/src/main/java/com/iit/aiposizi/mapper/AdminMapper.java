package com.iit.aiposizi.mapper;

import com.iit.aiposizi.entity.enums.AdminRoleEntity;
import com.iit.aiposizi.generated.model.AdminRole;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface AdminMapper {

    AdminMapper ADMIN_MAPPER = getMapper(AdminMapper.class);

    AdminRoleEntity toEntity(AdminRole entity);

}
