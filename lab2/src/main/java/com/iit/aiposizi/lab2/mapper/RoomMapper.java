package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.RoomEntity;
import com.iit.aiposizi.lab2.model.Room;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(builder = @Builder(disableBuilder = true), uses = OfficeMapper.class)
public interface RoomMapper {

    RoomMapper ROOM_MAPPER = getMapper(RoomMapper.class);

    @Mapping(target = "totalPlaces", expression = "java(entity.getPlaces() == null ? 0 : entity.getPlaces().size())")
    Room toModel(RoomEntity entity);

    @Mapping(target = "places", ignore = true)
    RoomEntity toEntity(Room room);

}
