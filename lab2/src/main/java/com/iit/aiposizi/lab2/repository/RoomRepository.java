package com.iit.aiposizi.lab2.repository;

import com.iit.aiposizi.lab2.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {

    boolean existsByOffice_IdAndNumber(UUID officeId, Integer number);

}
