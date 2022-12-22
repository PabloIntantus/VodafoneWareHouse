package com.vodafone.warehouse.repository;

import com.vodafone.warehouse.entity.DeviceEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Device repository.
 */
@Repository
public interface DeviceRepository extends MongoRepository<DeviceEntity, String> {
    List<DeviceEntity> findAll();

    /**
     * Find by status list.
     *
     * @param status the status
     * @return the list
     */
    @Query(value = "{status:'?0'}")
    List<DeviceEntity> findByStatus(String status);

    @Override
    void deleteById(String s);
}
