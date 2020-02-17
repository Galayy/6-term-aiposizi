package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.PlaceEntity;
import com.iit.aiposizi.lab2.model.Employee;
import com.iit.aiposizi.lab2.model.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface PlaceMapper {

    PlaceMapper PLACE_MAPPER = getMapper(PlaceMapper.class);

    @Mapping(target = "employee", source = "employee")
    @Mapping(target = "id", source = "place.id")
    @Mapping(target = "roomId", source = "place.room.id")
    Place toModel(PlaceEntity place, Employee employee);

}
