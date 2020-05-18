package com.iit.aiposizi.mapper;

import com.iit.aiposizi.entity.PlaceEntity;
import com.iit.aiposizi.generated.model.Place;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {RoomMapper.class, EmployeeMapper.class})
public interface PlaceMapper {

    PlaceMapper PLACE_MAPPER = getMapper(PlaceMapper.class);

    Place toModel(PlaceEntity place);

}
