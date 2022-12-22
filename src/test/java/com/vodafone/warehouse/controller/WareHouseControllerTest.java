package com.vodafone.warehouse.controller;

import com.vodafone.warehouse.entity.DeviceEntity;
import com.vodafone.warehouse.exception.WareHouseException;
import com.vodafone.warehouse.service.WareHouseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class WareHouseControllerTest {

    @Mock
    private WareHouseService wareHouseService;
    @InjectMocks
    private WareHouseController wareHouseController;

    @Test
    @DisplayName("Should return a device with status created")
    void newDeviceShouldReturnDeviceWithStatusCreated() throws WareHouseException {
        DeviceEntity device = new DeviceEntity();
        device.setId("123456789");
        device.setStatus("available");
        device.setTemperature(20);
        device.setPinCode("1234567");
        when(wareHouseService.addDevice()).thenReturn(device);
        ResponseEntity<DeviceEntity> response = wareHouseController.newDevice();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(device, response.getBody());
    }

    @Test
    @DisplayName("Should return empty list when there are no devices in the database")
    void avilabledevicesWhenThereAreNoDevicesInTheDatabaseThenReturnEmptyList() {
        List<DeviceEntity> deviceEntityList = new ArrayList<>();
        when(wareHouseService.getAvilablesDevice()).thenReturn(deviceEntityList);
        ResponseEntity<List<DeviceEntity>> responseEntity = wareHouseController.avilabledevices();
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(),responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return all devices when there are devices in the database")
    void avilabledevicesWhenThereAreDevicesInTheDatabaseThenReturnAllDevices() {
        List<DeviceEntity> deviceEntityList = new ArrayList<>();
        deviceEntityList.add(new DeviceEntity("1", "available", 0, "0"));
        deviceEntityList.add(new DeviceEntity("2", "available", 0, "0"));
        when(wareHouseService.getAvilablesDevice()).thenReturn(deviceEntityList);
        ResponseEntity<List<DeviceEntity>> response = wareHouseController.avilabledevices();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(deviceEntityList, response.getBody());
    }

    @Test
    @DisplayName("Should return an updatd device")
    void shouldReturnAnUpdatedDevice() throws WareHouseException {
        DeviceEntity device = new DeviceEntity();
        device.setId("123456789");
        device.setStatus("available");
        device.setTemperature(20);
        device.setPinCode("1234567");
        Optional<DeviceEntity> deviceEntityOptional = Optional.of(device);
        doReturn(deviceEntityOptional).when(wareHouseService).findById(any());
        doReturn(device).when(wareHouseService).updateDevice(any());
        ResponseEntity<DeviceEntity> response = wareHouseController.updateDevice("123456789","1234567");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(device, response.getBody());
    }

    @Test
    @DisplayName("Should return exception with pin code")
    void shouldReturnExceptionPinCode(){
        assertThrows(WareHouseException.class, () -> {
            wareHouseController.updateDevice("1","123");
        });

        assertThrows(WareHouseException.class, () -> {
            wareHouseController.updateDevice("1","!234567");
        });

    }

    @Test
    @DisplayName("Should return device not found exception")
    void shouldReturnDeviceNotFoundException() {
        DeviceEntity device = new DeviceEntity();
        device.setId("123456789");
        device.setStatus("available");
        device.setTemperature(20);
        device.setPinCode("1234567");
        when(wareHouseService.updateDevice(any())).thenReturn(device);
        assertThrows(WareHouseException.class, () -> {
            wareHouseController.updateDevice("123456789","1234567");
        });
    }

    @Test
    @DisplayName("Should delete a device and return 200")
    void shouldDeleteDeviceAndReturn200() throws WareHouseException {
        DeviceEntity device = new DeviceEntity();
        device.setId("123456789");
        device.setStatus("available");
        device.setTemperature(20);
        device.setPinCode("1234567");
        Optional<DeviceEntity> deviceEntityOptional = Optional.of(device);
        doReturn(deviceEntityOptional).when(wareHouseService).findById(any());
        doNothing().when(wareHouseService).deleteById(anyString());
        ResponseEntity<String> response = wareHouseController.deleteDevice("123456789");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return exception when cant find a device to delete")
    void shouldReturnExceptionInDelete() throws WareHouseException {
        assertThrows(WareHouseException.class, () -> {
            wareHouseController.deleteDevice("123456789");
        });
    }

}