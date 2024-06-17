package com.csi.service;

import com.csi.model.Employee;


import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    Employee signUp(Employee employee);

    boolean signIn(String empEmailId, String empPassword);

    List<Employee> findByName(String empName);

    Optional<Employee> findById(int empId);

    List<Employee> findAll();

    List<Employee> saveAll(List<Employee> employeeList);

    Employee update(Employee employee);

    void deleteById(int empId);

    void deleteAll();
}
