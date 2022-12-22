package com.vodafone.warehouse.service.impl;

import com.vodafone.warehouse.entity.DeviceEntity;
import com.vodafone.warehouse.exception.WareHouseException;
import com.vodafone.warehouse.repository.DeviceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
class WareHouseServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;
    @InjectMocks
    private WareHouseServiceImpl wareHouseService;

    DeviceEntity deviceTest = new DeviceEntity("1","READY",-1,"1234567");

    @Test
    @DisplayName("Should return a device with status ready")
    void addDeviceShouldReturnADeviceWithStatusReady() throws WareHouseException {

        when(deviceRepository.save(any())).thenReturn(deviceTest);
        DeviceEntity deviceEntity = wareHouseService.addDevice();
        assertEquals("READY", deviceEntity.getStatus());
        assertEquals(-1, deviceEntity.getTemperature());
    }


    @Test
    @DisplayName("Should return WareHouseException when try 10 times create a device with unique pin code")
    void addDeviceShouldReturnWareHouseException() {

        doThrow(DuplicateKeyException.class).when(deviceRepository).save(any());
        assertThrows(WareHouseException.class, () -> {
            wareHouseService.addDevice();
        });

    }

    @Test
    @DisplayName("Should return a list of active device")
    void shouldReturnListOfAvailableDevice() throws WareHouseException {
        List<DeviceEntity> availableDevices = new ArrayList<>();
        DeviceEntity device = new DeviceEntity("1", "ACTIVE", 2, "0000001");
        availableDevices.add(device);
        when(deviceRepository.findByStatus("ACTIVE")).thenReturn(availableDevices);
        List<DeviceEntity> deviceEntity = wareHouseService.getAvilablesDevice();
        assertEquals(1, deviceEntity.size());
    }

    @Test
    @DisplayName("Should return a device updated with pin code")
    void shouldReturnUpdatedDevice() throws WareHouseException {
        DeviceEntity device = new DeviceEntity("1", "ACTIVE", 2, "0000001");
        when(wareHouseService.updateDevice(any())).thenReturn(device);
        DeviceEntity deviceUpdated = wareHouseService.updateDevice(device);
        assertEquals("0000001", deviceUpdated.getPinCode());
    }

    @Test
    @DisplayName("Should return true when find a device with ID")
    void shouldReturnDeviceWithID() throws WareHouseException {
        DeviceEntity device = new DeviceEntity("1", "ACTIVE", 2, "0000001");
        Optional<DeviceEntity> deviceOptional = Optional.of(device);
        when(wareHouseService.findById(any())).thenReturn(deviceOptional);
        Optional<DeviceEntity> findDevice = wareHouseService.findById("1");
        assertTrue(findDevice.isPresent());
    }

    @Test
    @DisplayName("Should delete a device")
    void shouldDeleteDeviceWithID() throws WareHouseException {

        doNothing().when(deviceRepository).deleteById("1");
        wareHouseService.deleteById("1");
        assertTrue(true);
    }
}