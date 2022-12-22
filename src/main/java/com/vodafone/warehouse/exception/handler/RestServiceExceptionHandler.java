package com.vodafone.warehouse.exception.handler;

import com.mongodb.DuplicateKeyException;
import com.vodafone.warehouse.exception.WareHouseApiError;
import com.vodafone.warehouse.exception.WareHouseApiErrorList;
import com.vodafone.warehouse.exception.WareHouseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Rest service exception handler.
 */
@ControllerAdvice
public class RestServiceExceptionHandler {

    /**
     * Exception handler response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<WareHouseApiErrorList> exceptionHandler(Exception exception) {
        int code = 500;
        String message = "Something was wrong";
        String level = "Error";
        String description = "Something was wrong description";

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof WareHouseException wareHouseException) {
            code = wareHouseException.getCode();
            message = exception.getMessage();
            description = wareHouseException.getDescription();
            if (wareHouseException.getStatus() != null) {
                status = wareHouseException.getStatus();
                if (status.equals(HttpStatus.BAD_REQUEST)) {
                    code = 400;
                } else if (status.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
                    code = 500;
                } else if (status.equals(HttpStatus.NOT_FOUND)) {
                    code = 404;
                }
            }
        }else if(exception instanceof DuplicateKeyException duplicateKeyException){
            code = duplicateKeyException.getCode();
            message = "Pin code duplicated in DB";
            description = "Pin code duplicated in DB, try with another pin code";
        }

        WareHouseApiError error = new WareHouseApiError(code, message, level, description);
        WareHouseApiErrorList errors = new WareHouseApiErrorList();
        List<WareHouseApiError> errorList = new ArrayList<>();
        errorList.add(error);
        errors.setErrors(errorList);
        return new ResponseEntity<>(errors, status);
    }
}
