package com.paypal.bfs.test.employeeserv.exception;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BadRequestException extends Exception{

    List<Pair<String, String>> errors = new ArrayList<>();
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, List<Pair<String, String>> errors) {
        super(message);
        this.errors = errors;
    }
}

