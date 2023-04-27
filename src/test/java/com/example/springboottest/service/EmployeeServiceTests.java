package com.example.springboottest.service;

import com.example.springboottest.model.Employee;
import com.example.springboottest.repository.EmployeeRepository;
import com.example.springboottest.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.util.Optional;

public class EmployeeServiceTests {
    private EmployeeRepository employeeRepository;
    private EmployeeService employeeService;

    @BeforeEach
    public void setup(){
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImpl(employeeRepository);
    }

    @Test
    public void givenEmpObj_whenSaveEmp_thenReturnEmpObj(){
        // given
        Employee employee = Employee.builder()
                .id(1L)
                .firstName("shubham")
                .lastName("singh")
                .email("sh@s.com")
                .build();

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        BDDMockito.given(employeeRepository.save(employee))
                .willReturn(employee);

        // when
        Employee savedEmp = employeeService.saveEmployee(employee);

        // then
        Assertions.assertThat(savedEmp).isNotNull();
    }
}
