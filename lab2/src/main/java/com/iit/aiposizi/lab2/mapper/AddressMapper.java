package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.AddressEntity;
import com.iit.aiposizi.lab2.model.Address;
import com.iit.aiposizi.lab2.model.Office;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface AddressMapper {

    AddressMapper ADDRESS_MAPPER = getMapper(AddressMapper.class);

    @Mapping(target = "office", ignore = true)
    AddressEntity toEntity(Address address);

    Address toModel(AddressEntity entity);

}
