package com.iit.aiposizi.lab2.repository;

import com.iit.aiposizi.lab2.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {

    @Query(value = "SELECT * "
            + "FROM place "
            + "WHERE room_id = :roomId "
            + "ORDER BY number",
            nativeQuery = true)
    List<PlaceEntity> findAllByRoomId(@Param("roomId") UUID id);

    boolean existsByRoomIdAndNumber(UUID roomId, Integer number);

}
