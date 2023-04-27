package com.example.springboottest.service.impl;

import com.example.springboottest.exception.ResourceNotFoundException;
import com.example.springboottest.model.Employee;
import com.example.springboottest.repository.EmployeeRepository;
import com.example.springboottest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmp = employeeRepository.findByEmail(employee.getEmail());
        if(savedEmp.isPresent()){
            throw new ResourceNotFoundException("emp already exist with email "+ employee.getEmail());
        }
        return employeeRepository.save(employee);
    }
}
