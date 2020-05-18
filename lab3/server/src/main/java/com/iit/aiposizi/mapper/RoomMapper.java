package com.iit.aiposizi.mapper;

import com.iit.aiposizi.entity.OfficeEntity;
import com.iit.aiposizi.entity.RoomEntity;
import com.iit.aiposizi.generated.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {OfficeMapper.class, PlaceMapper.class})
public interface RoomMapper {

    RoomMapper ROOM_MAPPER = getMapper(RoomMapper.class);

    @Mapping(target = "totalPlaces", expression = "java(entity.getPlaces() == null ? 0 : entity.getPlaces().size())")
    Room toModel(RoomEntity entity);

    @Mapping(target = "places", ignore = true)
    @Mapping(target = "id", source = "room.id")
    @Mapping(target = "number", source = "room.number")
    @Mapping(target = "office", source = "office")
    RoomEntity toEntity(Room room, OfficeEntity office);

}
