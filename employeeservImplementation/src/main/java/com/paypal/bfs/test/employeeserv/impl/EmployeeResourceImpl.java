package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.Severity;
import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exception.BadRequestException;
import com.paypal.bfs.test.employeeserv.exception.RecordNotFoundException;
import com.paypal.bfs.test.employeeserv.mapper.EmployeeMapper;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.response.GenericResponse;
import com.paypal.bfs.test.employeeserv.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<GenericResponse<Employee>> employeeGetById(Long id) throws RecordNotFoundException {

        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(id);
        if(!employeeEntity.isPresent()) {
            throw new RecordNotFoundException(String.format("Record Doesn't Exist for id : %s", id));
        }
        Employee employee = EmployeeMapper.INSTANCE.employeeEntityToEmployee(employeeEntity.get());
        return GenericResponse
                .buildResponse(HttpStatus.OK,
                        "employee Found Sucessfully",
                        Severity.INFO,
                        employee);
    }

    @Override
    @Transactional
    public ResponseEntity<GenericResponse<Employee>> addEmployee(@Validated Employee employee, Errors errors) throws BadRequestException {

        if(errors.hasErrors()) {
            ValidatorUtil.resolveErrors(errors);
        }
        EmployeeEntity employeeEntity = EmployeeMapper.INSTANCE.employeeToEmployeeEntity(employee);
        EmployeeEntity result = employeeRepository.save(employeeEntity);
        Employee employee1 = EmployeeMapper.INSTANCE.employeeEntityToEmployee(result);

        return GenericResponse
                .buildResponse(HttpStatus.CREATED,
                        "employee added successfully",
                        Severity.INFO,
                        employee1);
    }
}
