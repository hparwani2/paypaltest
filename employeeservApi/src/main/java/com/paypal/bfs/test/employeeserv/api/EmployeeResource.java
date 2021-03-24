package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.exception.BadRequestException;
import com.paypal.bfs.test.employeeserv.exception.RecordNotFoundException;
import com.paypal.bfs.test.employeeserv.response.GenericResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
/**
 * Interface for employee resource operations.
 */
@RestController
@RequestMapping("/v1/bfs/employees")
public interface EmployeeResource {

    /**
     *
     * @param id
     * @return
     * @throws RecordNotFoundException
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<GenericResponse<Employee>> employeeGetById(@PathVariable("id") Long id) throws RecordNotFoundException;

    /**
     *
     * @param employee
     * @return
     */

    @RequestMapping(value = "", method = RequestMethod.POST)
    ResponseEntity<GenericResponse<Employee>> addEmployee(@RequestBody Employee employee, Errors errors) throws BadRequestException;
}
