package com.iit.aiposizi.repository;

import com.iit.aiposizi.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {

    @Query(value = "SELECT *"
            + "FROM place"
            + "         INNER JOIN office ON (:offices = '{}' OR office.company_name = ANY (CAST(:offices AS TEXT[])))"
            + "         INNER JOIN room ON office.id = room.office_id"
            + "    AND (:rooms = '{}' OR room.number = ANY (CAST(:rooms AS INTEGER[])))"
            + "WHERE room_id = room.id",
            nativeQuery = true)
    List<PlaceEntity> findAll(@Param("offices") String offices, @Param("rooms") String rooms);

    boolean existsByRoomIdAndNumber(UUID roomId, Integer number);

}
