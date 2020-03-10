package com.iit.aiposizi.lab2.repository;

import com.iit.aiposizi.lab2.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {

    boolean existsByOfficeIdAndNumber(UUID officeId, Integer number);

    @Query(value = "SELECT * "
            + "FROM room_view "
            + "WHERE office_id = :officeId "
            + "ORDER BY number",
            nativeQuery = true)
    List<RoomEntity> findAllByOfficeId(@Param("officeId") UUID id);

}
