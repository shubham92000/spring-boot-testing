package com.example.springboottest.service;

import com.example.springboottest.model.Employee;
import com.example.springboottest.repository.EmployeeRepository;
import com.example.springboottest.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("shubham")
                .lastName("singh")
                .email("sh@s.com")
                .build();
    }

    @Test
    public void givenEmpObj_whenSaveEmp_thenReturnEmpObj(){
        // given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee))
                .willReturn(employee);

        // when
        Employee savedEmp = employeeService.saveEmployee(employee);

        // then
        Assertions.assertThat(savedEmp).isNotNull();
    }
}
