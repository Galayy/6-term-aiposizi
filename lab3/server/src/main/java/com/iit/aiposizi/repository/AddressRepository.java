package com.iit.aiposizi.repository;

import com.iit.aiposizi.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {

    @Override
    @Modifying
    @Query(value = "DELETE FROM address CASCADE "
            + "WHERE id = :id",
            nativeQuery = true)
    void deleteById(@Param("id") UUID id);

}
