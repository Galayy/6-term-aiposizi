package com.iit.aiposizi.lab2.repository;

import com.iit.aiposizi.lab2.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeEntity, UUID> {

}
