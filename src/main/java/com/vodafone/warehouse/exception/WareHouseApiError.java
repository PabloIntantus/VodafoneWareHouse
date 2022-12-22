package com.vodafone.warehouse.exception;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Ware house api error.
 */
@Getter
@Setter
public class WareHouseApiError {
    /**
     * The Code.
     */
    @ApiModelProperty(value = "code of the error", example = "500")
    int code;

    /**
     * The Message.
     */
    @ApiModelProperty(value = "message with info about the error", example = "Request method not supported")
    String message;

    /**
     * The Level.
     */
    @ApiModelProperty(value = "Level", example = "Error level")
    String level;

    /**
     * The Description.
     */
    @ApiModelProperty(value = "Description with info about the error", example = "Missing mandatory params")
    String description;

    /**
     * Instantiates a new Ware house api error.
     *
     * @param code        the code
     * @param message     the message
     * @param level       the level
     * @param description the description
     */
    public WareHouseApiError(int code, String message, String level, String description) {
        this.code = code;
        this.message = message;
        this.level = level;
        this.description = description;
    }
}
