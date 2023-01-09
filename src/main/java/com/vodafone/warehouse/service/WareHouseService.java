package com.vodafone.warehouse.service;

import com.vodafone.warehouse.entity.DeviceEntity;
import com.vodafone.warehouse.exception.WareHouseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

/**
 * The interface Ware house service.
 */
public interface WareHouseService {

    /**
     * Add device device entity.
     *
     * @return the device entity
     */
    DeviceEntity addDevice() throws WareHouseException;

    /**
     * Gets avilables device.
     *
     * @return the avilables device
     */
    List<DeviceEntity> getAvilablesDevice();

    /**
     * Update device device entity.
     *
     * @param device the device
     * @return the device entity
     */
    DeviceEntity updateDevice(DeviceEntity device);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<DeviceEntity> findById(String id);

    /**
     * Delete by id.
     *
     * @param id the id
     */
    void deleteById(String id);

    Page<DeviceEntity> getPageableDevices(PageRequest of);
}
