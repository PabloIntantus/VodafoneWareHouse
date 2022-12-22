package com.vodafone.warehouse.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The type Ware house exception.
 */
@Getter
public class WareHouseException extends Exception {

    private int code;
    private HttpStatus status;

    /**
     * The Level.
     */
    String level = "error";

    /**
     * The Description.
     */
    String description = "something was wrong";

    /**
     * The Err.
     */
    Throwable err;

    /**
     * Instantiates a new Ware house exception.
     *
     * @param errorMenssage the error menssage
     * @param err           the err
     */
    public WareHouseException(String errorMenssage, Throwable err) {
        super(errorMenssage, err);
        if (err instanceof WareHouseException wareHouseException) {
            this.err = wareHouseException.getErr();
        } else {
            this.err = err;
        }
    }

    /**
     * Instantiates a new Ware house exception.
     *
     * @param message     the message
     * @param status      the status
     * @param description the description
     */
    public WareHouseException(String message, HttpStatus status, String description) {
        super(message);
        this.description = description;
        this.status = status;
    }
}
