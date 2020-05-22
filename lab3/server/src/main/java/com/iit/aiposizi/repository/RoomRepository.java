package com.iit.aiposizi.repository;

import com.iit.aiposizi.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, UUID> {

    @Query(value = "SELECT *"
            + "FROM room_view"
            + "         INNER JOIN office ON (:offices = '{}' OR office.company_name = ANY (CAST(:offices AS TEXT[])))"
            + "WHERE office_id = office.id",
            nativeQuery = true)
    List<RoomEntity> findAll(@Param("offices") String offices);

    @Override
    @Modifying
    @Query(value = "DELETE FROM room CASCADE "
            + "WHERE id = :id",
            nativeQuery = true)
    void deleteById(@Param("id") UUID id);

    boolean existsByOfficeIdAndNumber(UUID officeId, Integer number);

}
