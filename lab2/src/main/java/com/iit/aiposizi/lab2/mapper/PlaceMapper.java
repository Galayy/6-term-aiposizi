package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.PlaceEntity;
import com.iit.aiposizi.lab2.model.Place;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(builder = @Builder(disableBuilder = true), uses = {RoomMapper.class, EmployeeMapper.class})
public interface PlaceMapper {

    PlaceMapper PLACE_MAPPER = getMapper(PlaceMapper.class);

    Place toModel(PlaceEntity place);

}
