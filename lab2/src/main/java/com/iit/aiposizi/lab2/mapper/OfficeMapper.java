package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.OfficeEntity;
import com.iit.aiposizi.lab2.model.Office;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(builder = @Builder(disableBuilder = true), uses = AddressMapper.class)
public interface OfficeMapper {

    OfficeMapper OFFICE_MAPPER = getMapper(OfficeMapper.class);

    @Mapping(target = "roomsNumber", expression = "java(entity.getRooms() == null? 0 : entity.getRooms().size())")
    Office toModel(OfficeEntity entity);

    @Mapping(target = "rooms", ignore = true)
    OfficeEntity toEntity(Office office);

}

