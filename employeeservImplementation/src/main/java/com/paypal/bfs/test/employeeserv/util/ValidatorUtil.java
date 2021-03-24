package com.paypal.bfs.test.employeeserv.util;

import com.paypal.bfs.test.employeeserv.exception.BadRequestException;
import com.paypal.bfs.test.employeeserv.exception.Pair;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import java.util.ArrayList;
import java.util.List;

public class ValidatorUtil {

    public static void resolveErrors(Errors errors) throws BadRequestException {
        List<Pair<String, String>> errorList = new ArrayList<>();
        for(FieldError fieldError : errors.getFieldErrors()) {
            errorList.add(new Pair<>(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        throw new BadRequestException("Validation Errors", errorList);
    }
}
