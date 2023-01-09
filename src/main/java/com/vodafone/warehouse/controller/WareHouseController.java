package com.vodafone.warehouse.controller;

import com.vodafone.warehouse.entity.DeviceEntity;
import com.vodafone.warehouse.dto.DeviceResponseDTO;
import com.vodafone.warehouse.exception.WareHouseApiErrorList;
import com.vodafone.warehouse.exception.WareHouseException;
import com.vodafone.warehouse.service.WareHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * The type Ware house controller.
 */
@RestController
@RequestMapping(value = "/warehouse")
@Api(value = "WareHouse API",
        produces = "application/json",
        consumes = "application/json")
public class WareHouseController {
    @Autowired
    private WareHouseService wareHouseService;

    /**
     * New device response entity.
     *
     * @return the response entity
     */
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Creted", response = DeviceEntity.class)})
    @PostMapping(value = "/newdevice")
    public ResponseEntity<DeviceEntity> newDevice() throws WareHouseException {
        DeviceEntity device = wareHouseService.addDevice();
        return new ResponseEntity<>(device, HttpStatus.CREATED);
    }

    /**
     * Avilabledevices response entity.
     *
     * @return the response entity
     */
    @GetMapping(value = "/avilable-devices")
    public ResponseEntity<List<DeviceEntity>> avilabledevices() {
        List<DeviceEntity> deviceEntityList = wareHouseService.getAvilablesDevice();
        return new ResponseEntity<>(deviceEntityList, HttpStatus.OK);

    }

    @GetMapping(value = "/pageable-devices")
    public ResponseEntity<DeviceResponseDTO> avilabledevices(@RequestParam int page, int size) {
        Page<DeviceEntity> deviceEntityList = wareHouseService.getPageableDevices(PageRequest.of(page,size));
        DeviceResponseDTO deviceResponseDTO = new DeviceResponseDTO();
        deviceResponseDTO.setContent(deviceEntityList.getContent());
        deviceResponseDTO.setTotalElements(deviceEntityList.getTotalElements());
        return new ResponseEntity<>(deviceResponseDTO, HttpStatus.OK);

    }

    /**
     * Update device response entity.
     *
     * @param id      the id
     * @param pinCode the pin code
     * @return the response entity
     */
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DeviceEntity.class),
            @ApiResponse(code = 400, message = "Bad request", response = WareHouseApiErrorList.class, responseContainer = "List")})
    @PatchMapping(value = "/device/{id}")
    public ResponseEntity<DeviceEntity> updateDevice(@ApiParam(value = "Id of device", example = "63a2e81c2f3d95676fb0ec66", required = true)
                                                     @PathVariable(required = true) String id,
                                                     @ApiParam(value = "Device pin code", example = "1234567", required = true)
                                                     @RequestHeader(required = true) String pinCode) throws WareHouseException {
        if (Pattern.matches("\\d+", pinCode) && pinCode.length() == 7) {
            Optional<DeviceEntity> optinalDevice = wareHouseService.findById(id);
            if (optinalDevice.isPresent()) {
                DeviceEntity device = optinalDevice.get();
                device.setPinCode(pinCode);
                DeviceEntity updatedDevice = wareHouseService.updateDevice(device);
                return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
            } else {
                throw new WareHouseException("Device not found", HttpStatus.NOT_FOUND, "Cant update device because cant find it in DB");
            }
        } else {
            throw new WareHouseException("Pin code incorrect", HttpStatus.BAD_REQUEST, "Pin code must be a number of seven digits");
        }

    }

    /**
     * Delete device response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping(value = "/device/{id}")
    public ResponseEntity<String> deleteDevice(@ApiParam(value = "Device ID", example = "63a2e81c2f3d95676fb0ec66", required = true)
                                               @PathVariable(required = true) String id) throws WareHouseException {

        Optional<DeviceEntity> device = wareHouseService.findById(id);
        if (device.isPresent()) {
            wareHouseService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new WareHouseException("Device not found", HttpStatus.NOT_FOUND, "Cant update device because cant find it in DB");
        }
    }

}
