package com.iit.aiposizi.repository;

import com.iit.aiposizi.entity.OfficeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeEntity, UUID> {

    @Override
    @Modifying
    @Query(value = "DELETE FROM office CASCADE "
            + "WHERE id = :id",
            nativeQuery = true)
    void deleteById(@Param("id") UUID id);

}
