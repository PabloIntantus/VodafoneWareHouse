package com.vodafone.warehouse.service.impl;


import com.vodafone.warehouse.entity.DeviceEntity;
import com.vodafone.warehouse.exception.WareHouseException;
import com.vodafone.warehouse.repository.DeviceRepository;
import com.vodafone.warehouse.service.WareHouseService;
import com.vodafone.warehouse.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * The type Ware house service.
 */
@Service
public class WareHouseServiceImpl implements WareHouseService {

    @Autowired
    private DeviceRepository deviceRepository;
    private static final String READY = "READY";
    private static final String ACTIVE = "ACTIVE";

    public DeviceEntity addDevice() throws WareHouseException {

        DeviceEntity deviceEntity = new DeviceEntity();
        deviceEntity.setStatus(READY);
        deviceEntity.setTemperature(-1);
        boolean createdDevice = false;
        DeviceEntity savedDevice = null;
        int maxEntries = 10;
        while (!createdDevice) {
            try {
                deviceEntity.setPinCode(Utils.randomString(false, true, 7));
                savedDevice = deviceRepository.save(deviceEntity);
                createdDevice = true;
            } catch (DuplicateKeyException e) {
                maxEntries--;
                if (maxEntries == 0) {
                    throw new WareHouseException("Error creating the device", HttpStatus.INTERNAL_SERVER_ERROR, "Too many try to create a device with an unique pin code");
                }
            }
        }
        return savedDevice;
    }

    public List<DeviceEntity> getAvilablesDevice() {
        List<DeviceEntity> deviceEntityList = deviceRepository.findByStatus(ACTIVE);
        //Return collection sorted
        //I could order it by query but I did it to have an example with stream
        return deviceEntityList.stream().sorted(Comparator.comparing(DeviceEntity::getPinCode)).toList();
    }

    public DeviceEntity updateDevice(DeviceEntity device) {
        return deviceRepository.save(device);
    }

    public Optional<DeviceEntity> findById(String id) {
        return deviceRepository.findById(id);
    }

    public void deleteById(String id) {
        deviceRepository.deleteById(id);
    }

    public Page<DeviceEntity> getPageableDevices(PageRequest of){
        return deviceRepository.findAll(of);
    }
}
