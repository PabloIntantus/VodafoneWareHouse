package com.vodafone.warehouse.repository;


import com.vodafone.warehouse.entity.DeviceEntity;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * The type Decive repository tests.
 */
@Testcontainers
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
class DeciveRepositoryTests {
    /**
     * The constant mongoDBContainer.
     */
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    /**
     * Sets properties.
     *
     * @param registry the registry
     */
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * Clean up.
     */
    @AfterClass
    void cleanUp() {
        this.deviceRepository.deleteAll();
    }

    @Test
    void shouldCreateADevice() {
        DeviceEntity expectedEntity = new DeviceEntity("5", "READY", -1, "1234565");
        DeviceEntity savedEntity = deviceRepository.save(expectedEntity);
        assertEquals(expectedEntity, savedEntity);
    }

    @Test
    void duplicateKeyExceptionWithUniquePinCode() {
        DeviceEntity device1 = new DeviceEntity("5", "READY", -1, "0000001");
        deviceRepository.save(device1);

        DeviceEntity device2 = new DeviceEntity("99", "ACTIVE", 10, "0000001");
        assertThrows(DuplicateKeyException.class, () -> {
            deviceRepository.save(device2);
        });
    }

    /**
     * Should return list of device with status active.
     */
    @Test
    void shouldReturnListOfDeviceWithStatusACTIVE() {
        this.deviceRepository.save(new DeviceEntity("1", "ACTIVE", 4, "1234561"));
        this.deviceRepository.save(new DeviceEntity("2", "ACTIVE", 5, "1234562"));
        this.deviceRepository.save(new DeviceEntity("3", "ACTIVE", 6, "1234563"));
        this.deviceRepository.save(new DeviceEntity("4", "READY", -1, "1234564"));

        List<DeviceEntity> customers = deviceRepository.findByStatus("ACTIVE");
        System.out.println("Customer id: " + customers.get(0).getId());
        assertEquals(3, customers.size());
    }


    @Test
    void shouldDeleteADevice() {
        Optional<DeviceEntity> entity = deviceRepository.findById("1");
        deviceRepository.deleteById("1");
        Optional<DeviceEntity> entity2 = deviceRepository.findById("1");
        assertTrue(entity.isPresent());
        assertFalse(entity2.isPresent());
    }

    @Test
    void shouldUpdateADevice() {
        Optional<DeviceEntity> optionalDevice = deviceRepository.findById("2");
        String pinCode = "0";
        String pinCodeUpdated = "0";
        if (optionalDevice.isPresent()) {
            DeviceEntity deviceEntity = optionalDevice.get();
            pinCode = deviceEntity.getPinCode();
            deviceEntity.setPinCode("1111111");
            DeviceEntity updatedEntity = deviceRepository.save(deviceEntity);
            pinCodeUpdated = updatedEntity.getPinCode();
            assertEquals(deviceEntity.getId(), updatedEntity.getId());
        }

        assertNotEquals(pinCode, pinCodeUpdated);
    }
}
