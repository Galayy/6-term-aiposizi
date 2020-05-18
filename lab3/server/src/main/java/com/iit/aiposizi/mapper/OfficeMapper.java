package com.iit.aiposizi.mapper;

import com.iit.aiposizi.entity.OfficeEntity;
import com.iit.aiposizi.generated.model.Office;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = AddressMapper.class)
public interface OfficeMapper {

    OfficeMapper OFFICE_MAPPER = getMapper(OfficeMapper.class);

    @Mapping(target = "address", expression = "java(entity.getAddress().toString())")
    @Mapping(target = "roomsNumber", expression = "java(entity.getRooms() == null? 0 : entity.getRooms().size())")
    Office toModel(OfficeEntity entity);

}

