package com.example.springboottest.repository;

import com.example.springboottest.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("shubham")
                .lastName("singh")
                .email("s@s.com")
                .build();
    }

    @DisplayName("junit test for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnedSaveEmployee(){
        Employee savedEmployee = employeeRepository.save(employee);

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){
        Employee employee1 = Employee.builder()
                .firstName("sneha")
                .lastName("singh")
                .email("s@s.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        List<Employee> employeeList = employeeRepository.findAll();

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmpObj(){
        employeeRepository.save(employee);

        Employee employeedb = employeeRepository.findById(employee.getId()).get();

        assertThat(employeedb).isNotNull();
    }

    @Test
    public void givenEmpEmail_whenFindByEmail_thenReturnEmpObj(){
        employeeRepository.save(employee);

        Employee employeedb = employeeRepository.findByEmail(employee.getEmail()).get();

        assertThat(employeedb).isNotNull();
    }

    @Test
    public void givenEmpObj_whenUpdateEmp_thenReturnUpdatedEmp(){
        employeeRepository.save(employee);

        Employee savedEmp = employeeRepository.findById(employee.getId()).get();
        savedEmp.setEmail("s@gmail.com");

        Employee updatedEmp = employeeRepository.save(savedEmp);

        assertThat(updatedEmp.getEmail()).isEqualTo("s@gmail.com");
    }

    @Test
    public void givenEmpObj_whenDelete_thenRemoveEmp(){
        employeeRepository.save(employee);

        employeeRepository.delete(employee);

        Optional<Employee> empOpt = employeeRepository.findById(employee.getId());

        assertThat(empOpt).isEmpty();
    }

    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmpObj(){
        employeeRepository.save(employee);

        String firstName = "shubham";
        String lastName = "singh";

        Employee savedEmp = employeeRepository.findByJPQL(firstName, lastName);

        assertThat(savedEmp).isNotNull();
    }

    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamed_thenReturnEmpObj(){
        employeeRepository.save(employee);

        String firstName = "shubham";
        String lastName = "singh";

        Employee savedEmp = employeeRepository.findByJPQLNamed(firstName, lastName);

        assertThat(savedEmp).isNotNull();
    }

    @Test
    public void givenFirstNameAndLastName_whenFindByNative_thenReturnEmpObj(){
        employeeRepository.save(employee);

        String firstName = "shubham";
        String lastName = "singh";

        Employee savedEmp = employeeRepository.findByNativeSQLNamed(firstName, lastName);

        assertThat(savedEmp).isNotNull();
    }

    @Test
    public void givenFirstNameAndLastName_whenFindByNativeNamed_thenReturnEmpObj(){
        employeeRepository.save(employee);

        String firstName = "shubham";
        String lastName = "singh";

        Employee savedEmp = employeeRepository.findByJPQLNamed(firstName, lastName);

        assertThat(savedEmp).isNotNull();
    }
}
