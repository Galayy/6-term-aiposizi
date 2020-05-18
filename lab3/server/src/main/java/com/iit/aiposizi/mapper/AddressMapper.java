package com.iit.aiposizi.mapper;

import com.iit.aiposizi.entity.AddressEntity;
import com.iit.aiposizi.generated.model.Address;
import com.iit.aiposizi.generated.model.AddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface AddressMapper {

    AddressMapper ADDRESS_MAPPER = getMapper(AddressMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "office", ignore = true)
    AddressEntity toEntity(AddressRequest address);

    Address toModel(AddressEntity entity);

}
