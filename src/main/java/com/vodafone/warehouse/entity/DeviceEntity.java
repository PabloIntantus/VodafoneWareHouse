package com.vodafone.warehouse.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The type Device entity.
 */
@Document(collection = "device")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceEntity {
    @Id
    private String id;
    private String status;
    private int temperature;
    @Indexed(unique = true)
    private String pinCode;
}
