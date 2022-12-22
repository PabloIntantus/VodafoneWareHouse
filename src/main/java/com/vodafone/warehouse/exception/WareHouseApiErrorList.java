package com.vodafone.warehouse.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * The type Ware house api error list.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseApiErrorList {
    /**
     * The Errors.
     */
    @ApiModelProperty(value = "List of errors")
    List<WareHouseApiError> errors;
}
