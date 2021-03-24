package com.paypal.bfs.test.employeeserv.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.paypal.bfs.test.employeeserv.Severity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponse<T> {

    private UUID id = UUID.randomUUID();

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private Date timestamp;

    private HttpStatus status;
    
    private Integer code;

    private String message;

    private Severity severity;

    private T body;

    private GenericResponse() {
        //shouldn't be initialized outside the class
    }

    private GenericResponse(HttpStatus status, String message, Severity severity, T body) {
        this();
        this.status = status;
        this.message = message;
        this.severity = severity;
        this.body = body;
        this.code = status.value();
        this.timestamp = new Date();
    }

    public static <T> ResponseEntity<GenericResponse<T>> buildResponse(HttpStatus status, String message, Severity severity, T body) {
        GenericResponse<T> genericResponse = new GenericResponse<>(status, message, severity, body);
        return ResponseEntity
                .status(status)
                .body(genericResponse);
    }
}
