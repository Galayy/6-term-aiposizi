package com.iit.aiposizi.repository;

import com.iit.aiposizi.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    @Query(value = "SELECT *"
            + "FROM employee "
            + "WHERE :employeeIds = '{}' OR id = ANY (CAST(:employeeIds AS UUID[]))",
            nativeQuery = true)
    List<EmployeeEntity> findAll(@Param("employeeIds") String employeeIds);

    boolean existsByLastName(String lastName);

}
