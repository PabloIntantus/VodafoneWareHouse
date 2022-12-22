package com.vodafone.warehouse.exception.handler;

import com.vodafone.warehouse.exception.WareHouseApiErrorList;
import com.vodafone.warehouse.exception.WareHouseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RestServiceExceptionHandlerTest {

    @InjectMocks
    private RestServiceExceptionHandler restServiceExceptionHandler;

    @Test
    @DisplayName(
            "Should return 500 when the exception is warehouseexception and status is internal_server_error")
    void
    exceptionHandlerWhenExceptionIsWareHouseExceptionAndStatusIsInternalServerErrorThenReturn500() {
        WareHouseException exception =
                new WareHouseException("message", HttpStatus.INTERNAL_SERVER_ERROR, "description");
        ResponseEntity<WareHouseApiErrorList> responseEntity =
                restServiceExceptionHandler.exceptionHandler(exception);
        assertEquals(500, responseEntity.getBody().getErrors().get(0).getCode());
    }

    @Test
    @DisplayName(
            "Should return 400 when the exception is warehouseexception and status is bad_request")
    void exceptionHandlerWhenExceptionIsWareHouseExceptionAndStatusIsBadRequestThenReturn400() {
        WareHouseException exception =
                new WareHouseException("message", HttpStatus.BAD_REQUEST, "description");
        ResponseEntity<WareHouseApiErrorList> responseEntity =
                restServiceExceptionHandler.exceptionHandler(exception);
        assertEquals(400, responseEntity.getStatusCodeValue());
    }

    @Test
    @DisplayName(
            "Should return 404 when the exception is warehouseexception and status is not_found")
    void exceptionHandlerWhenExceptionIsWareHouseExceptionAndStatusIsNotFoundThenReturn404() {
        WareHouseException exception =
                new WareHouseException("message", HttpStatus.NOT_FOUND, "description");
        ResponseEntity<WareHouseApiErrorList> responseEntity =
                restServiceExceptionHandler.exceptionHandler(exception);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should return 500 when the exception is not warehouseexception")
    void exceptionHandlerWhenExceptionIsNotWareHouseExceptionThenReturn500() {
        Exception exception = new Exception("something was wrong");
        ResponseEntity<WareHouseApiErrorList> responseEntity =
                restServiceExceptionHandler.exceptionHandler(exception);
        assertEquals(500, responseEntity.getBody().getErrors().get(0).getCode());
        assertEquals(
                "Something was wrong", responseEntity.getBody().getErrors().get(0).getMessage());
        assertEquals("Error", responseEntity.getBody().getErrors().get(0).getLevel());
        assertEquals(
                "Something was wrong description",
                responseEntity.getBody().getErrors().get(0).getDescription());
    }
}