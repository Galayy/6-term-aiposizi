package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.OfficeEntity;
import com.iit.aiposizi.lab2.entity.RoomEntity;
import com.iit.aiposizi.lab2.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface RoomMapper {

    RoomMapper ROOM_MAPPER = getMapper(RoomMapper.class);

    @Mapping(target = "id", expression = "java(entity.getId())")
    @Mapping(target = "officeId", expression = "java(entity.getOffice().getId())")
    @Mapping(target = "number", expression = "java(entity.getNumber())")
    Room toModel(RoomEntity entity);

    @Mapping(target = "places", ignore = true)
    @Mapping(target = "office", source = "office")
    @Mapping(target = "id", expression = "java(entity.getId())")
    @Mapping(target = "number", expression = "java(entity.getNumber())")
    RoomEntity toEntity(Room entity, OfficeEntity office);

}
