package com.vodafone.warehouse.dto;

import com.vodafone.warehouse.entity.DeviceEntity;
import lombok.*;

import java.util.List;

/**
 * The type Device entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponseDTO {

    private long totalElements;
    private List<DeviceEntity> content;
}
