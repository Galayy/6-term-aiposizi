package com.iit.aiposizi.lab2.repository;

import com.iit.aiposizi.lab2.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, UUID> {

    Optional<EmployeeEntity> findByLastName(String lastName);

    boolean existsByLastName(String lastName);

}
