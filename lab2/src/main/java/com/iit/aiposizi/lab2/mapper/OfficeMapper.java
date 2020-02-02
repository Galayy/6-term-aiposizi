package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.AddressEntity;
import com.iit.aiposizi.lab2.entity.OfficeEntity;
import com.iit.aiposizi.lab2.model.Office;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface OfficeMapper {

    OfficeMapper OFFICE_MAPPER = getMapper(OfficeMapper.class);

    @Mapping(target = "addressId", expression = "java(entity.getAddress().getId())")
    Office toModel(OfficeEntity entity);

    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "address", source = "address")
    @Mapping(target = "id", expression = "java(office.getId())")
    OfficeEntity toEntity(Office office, AddressEntity address);

}

