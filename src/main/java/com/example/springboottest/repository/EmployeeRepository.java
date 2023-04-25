package com.example.springboottest.repository;

import com.example.springboottest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    // Employee class is used and not mysql table ( JPQl query with index params )
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);

    // Employee class is used and not mysql table ( JPQl query with names params )
    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJPQLNamed(@Param("firstName") String firstName,@Param("lastName") String lastName);

    // actual mysql table used ( JPQl query with index params )
    @Query(
            value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2",
            nativeQuery = true
    )
    Employee findByNativeSQL(String firstName, String lastName);

    // actual mysql table used ( JPQl query with named params )
    @Query(
            value = "select * from employees e where e.first_name = :first_name and e.last_name = :last_name",
            nativeQuery = true
    )
    Employee findByNativeSQLNamed(@Param("first_name") String firstName,@Param("last_name") String lastName);
}
