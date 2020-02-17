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

    @Mapping(target = "officeId", source = "entity.office.id")
    @Mapping(target = "totalPlacesNumber", expression = "java(entity.getPlaces().size())")
    Room toModel(RoomEntity entity);

    @Mapping(target = "places", ignore = true)
    @Mapping(target = "office", source = "office")
    @Mapping(target = "id", source = "room.id")
    @Mapping(target = "number", source = "room.number")
    RoomEntity toEntity(Room room, OfficeEntity office);

}
