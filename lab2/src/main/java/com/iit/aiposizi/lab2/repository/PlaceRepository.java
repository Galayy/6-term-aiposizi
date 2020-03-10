package com.iit.aiposizi.lab2.repository;

import com.iit.aiposizi.lab2.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, UUID> {

    @Query(value = "SELECT * "
            + "FROM place "
            + "WHERE room_id = :roomId "
            + "ORDER BY number",
            nativeQuery = true)
    List<PlaceEntity> findAllByRoomId(@Param("roomId") UUID id);

    @Query(value = "SELECT * "
            + "FROM place "
            + "INNER JOIN office ON office.company_name = :companyName "
            + "INNER JOIN room ON office_id = office.id AND room.number = :roomNumber "
            + "WHERE room_id = room.id AND place.number = :number",
            nativeQuery = true)
    Optional<PlaceEntity> findByCompanyNameAndRoomNumberAndNumber(@Param("companyName") String companyName,
                                                                  @Param("roomNumber") Integer roomNumber,
                                                                  @Param("number") Integer number);

    @Query(value = "SELECT * "
            + "FROM place "
            + "WHERE employee_id = :employeeId",
            nativeQuery = true)
    Optional<PlaceEntity> findByEmployeeId(@Param("employeeId") UUID employeeId);

    boolean existsByRoomIdAndNumber(UUID roomId, Integer number);

}
