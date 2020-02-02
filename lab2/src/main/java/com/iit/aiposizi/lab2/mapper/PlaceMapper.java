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
    @Mapping(target = "id", expression = "java(place.getId())")
    @Mapping(target = "number", expression = "java(place.getNumber())")
    @Mapping(target = "roomId", expression = "java(place.getRoom().getId())")
    Place toModel(PlaceEntity place, Employee employee);

}
