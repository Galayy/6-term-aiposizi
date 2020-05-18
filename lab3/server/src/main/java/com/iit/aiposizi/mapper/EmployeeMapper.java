package com.iit.aiposizi.mapper;

import com.iit.aiposizi.entity.EmployeeEntity;
import com.iit.aiposizi.generated.model.Employee;
import com.iit.aiposizi.generated.model.EmployeeRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper EMPLOYEE_MAPPER = getMapper(EmployeeMapper.class);

    Employee toModel(EmployeeEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "place", ignore = true)
    EmployeeEntity toEntity(EmployeeRequest employee);

}
