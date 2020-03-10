package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.AddressEntity;
import com.iit.aiposizi.lab2.model.Address;
import org.mapstruct.Mapper;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface AddressMapper {

    AddressMapper ADDRESS_MAPPER = getMapper(AddressMapper.class);

    AddressEntity toEntity(Address address);

    Address toModel(AddressEntity entity);

}
