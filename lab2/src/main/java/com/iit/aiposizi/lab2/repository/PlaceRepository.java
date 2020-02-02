package com.iit.aiposizi.lab2.repository;

import com.iit.aiposizi.lab2.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {

    boolean existsByEmployee_LastNameAndRoom_IdAndNumber(String lastName, UUID roomId, Integer number);

}
