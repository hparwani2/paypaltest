package com.paypal.bfs.test.employeeserv.exceptionhandler;

import com.paypal.bfs.test.employeeserv.Severity;
import com.paypal.bfs.test.employeeserv.exception.BadRequestException;
import com.paypal.bfs.test.employeeserv.exception.RecordNotFoundException;
import com.paypal.bfs.test.employeeserv.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GenericExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

    @ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity<GenericResponse<Object>> recordNotFoundHandler(RecordNotFoundException recordNotFoundException) {
        logger.error("RecordNotFoundException Occured {}", recordNotFoundException);
        return GenericResponse
                .buildResponse(HttpStatus.NOT_FOUND,
                        recordNotFoundException.getMessage(),
                        Severity.ERROR,
                        null);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<GenericResponse<Object>> badRequestExceptionHandler(BadRequestException badRequestException) {
        logger.error("BadRequest Exception {} Occured", badRequestException);
        return GenericResponse
                .buildResponse(HttpStatus.BAD_REQUEST,
                        badRequestException.getMessage(),
                        Severity.ERROR,
                        badRequestException.getErrors());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GenericResponse<Object>> exceptionHandler(Exception exception) {
        logger.error("Exception Occured {}", exception);
        return GenericResponse
                .buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        exception.getMessage(),
                        Severity.ERROR,
                        null);
    }
}
