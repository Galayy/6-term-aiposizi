package com.iit.aiposizi.lab2.mapper;

import com.iit.aiposizi.lab2.entity.EmployeeEntity;
import com.iit.aiposizi.lab2.model.Employee;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(builder = @Builder(disableBuilder = true), uses = {OfficeMapper.class, RoomMapper.class})
public interface EmployeeMapper {

    EmployeeMapper EMPLOYEE_MAPPER = getMapper(EmployeeMapper.class);

    Employee toModel(EmployeeEntity entity);

    @Mapping(target = "place", ignore = true)
    EmployeeEntity toEntity(Employee employee);

}
