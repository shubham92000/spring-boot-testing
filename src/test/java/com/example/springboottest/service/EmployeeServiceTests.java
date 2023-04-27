package com.example.springboottest.service;

import com.example.springboottest.exception.ResourceNotFoundException;
import com.example.springboottest.model.Employee;
import com.example.springboottest.repository.EmployeeRepository;
import com.example.springboottest.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
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

    @Test
    public void givenExistingEmail_whenSaveEmp_thenThrowsException(){
        // given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,() -> {
            employeeService.saveEmployee(employee);
        });

        Mockito.verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    public void givenEmpList_whenGetAllEmp_thenReturnEmpList(){
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("sneha")
                .lastName("singh")
                .email("sn@s.com")
                .build();

        given(employeeRepository.findAll())
                .willReturn(List.of(employee, employee1));

        List<Employee> employeeList = employeeService.getAllEmployees();

        Assertions.assertThat(employeeList).isNotNull();
        Assertions.assertThat(employeeList.size()).isEqualTo(2);
    }

    @DisplayName("junit test for getAllEmp method (negative scenario) ")
    @Test
    public void givenEmptyEmpList_whenGetAllEmp_thenReturnEmptyEmpList(){
        Employee employee1 = Employee.builder()
                .id(1L)
                .firstName("sneha")
                .lastName("singh")
                .email("sn@s.com")
                .build();

        given(employeeRepository.findAll())
                .willReturn(Collections.emptyList());

        List<Employee> employeeList = employeeService.getAllEmployees();

        Assertions.assertThat(employeeList).isEmpty();
        Assertions.assertThat(employeeList.size()).isEqualTo(0);
    }

    @Test
    public void givenEmpId_whenGetEmpById_thenReturnEmpObj(){
        given(employeeRepository.findById(employee.getId()))
                .willReturn(Optional.of(employee));

        Employee savedEmp = employeeService.getEmployeeById(employee.getId()).get();

        Assertions.assertThat(savedEmp).isNotNull();
    }

    @Test
    public void givenEmployee_whenUpdateEmp_thenReturnUpdatedEmp(){
        given(employeeRepository.save(employee))
                .willReturn(employee);

        employee.setEmail("john@gmail.com");
        employee.setFirstName("john");

        Employee updatedEmp = employeeService.updateEmployee(employee);

        Assertions.assertThat(updatedEmp.getEmail()).isEqualTo("john@gmail.com");
        Assertions.assertThat(updatedEmp.getFirstName()).isEqualTo("john");
    }

    @Test
    public void givenEmpId_whenDeleteEmp_thenNothing(){
        long id = 1L;

        willDoNothing().given(employeeRepository).deleteById(id);

        employeeService.deleteEmployee(id);

        verify(employeeRepository, times(1)).deleteById(id);
    }
}
