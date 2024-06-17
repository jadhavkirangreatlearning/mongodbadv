package com.csi.controller;

import com.csi.exception.RecordNotFoundException;
import com.csi.model.Employee;
import com.csi.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Slf4j
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<Employee> signUp(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.signUp(employee));
    }

    @GetMapping("/signin/{empEmailId}/{empPassword}")
    public ResponseEntity<Boolean> signIn(@PathVariable String empEmailId, @PathVariable String empPassword) {
        return ResponseEntity.ok(employeeService.signIn(empEmailId, empPassword));
    }

    @GetMapping("/findbyid/{empId}")
    public ResponseEntity<Optional<Employee>> findById(@PathVariable int empId) {
        return ResponseEntity.ok(employeeService.findById(empId));
    }

    @GetMapping("/findbyname/{empName}")
    public ResponseEntity<List<Employee>> findByName(@PathVariable String empName) {
        return ResponseEntity.ok(employeeService.findByName(empName));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/findbycontactnumber/{empContactNumber}")
    public ResponseEntity<Employee> findByContactNumber(@PathVariable long empContactNumber) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> emp.getEmpContactNumber() == empContactNumber).toList().get(0));
    }

    @GetMapping("/findbyuid/{empUID}")
    public ResponseEntity<Employee> findByUID(@PathVariable long empUID) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> emp.getEmpUID() == empUID).toList().get(0));
    }

    @GetMapping("/finbyempemailid/{empEmailId}")
    public ResponseEntity<Employee> findByEmailId(@PathVariable String empEmailId) {
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> emp.getEmpEmailId().equals(empEmailId)).toList().get(0));
    }

    @GetMapping("/findbydob/{empDOB}")
    public ResponseEntity<List<Employee>> findByDOB(@PathVariable String empDOB) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(empDOB)).toList());
    }

    @GetMapping("/findbyanyinput/{input}")
    public ResponseEntity<List<Employee>> findByAnyInput(@PathVariable String input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return ResponseEntity.ok(employeeService.findAll().stream().filter(emp -> simpleDateFormat.format(emp.getEmpDOB()).equals(input)
                || emp.getEmpName().equals(input)
                || emp.getEmpEmailId().equals(input)
                || String.valueOf(emp.getEmpId()).equals(input)
                || String.valueOf(emp.getEmpContactNumber()).equals(input)
                || String.valueOf(emp.getEmpUID()).equals(input)
                || emp.getEmpPanCard().equals(input)).toList());

    }

    @GetMapping("/sortbyid")
    public ResponseEntity<List<Employee>> sortById() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpId)).toList());

    }

    @GetMapping("/sortbyname")
    public ResponseEntity<List<Employee>> sortByName() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpName)).toList());

    }

    @GetMapping("/sortbysalary")
    public ResponseEntity<List<Employee>> sortBySalary() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpSalary)).toList());

    }

    @GetMapping("/sortbydob")
    public ResponseEntity<List<Employee>> sortByDOB() {
        return ResponseEntity.ok(employeeService.findAll().stream().sorted(Comparator.comparing(Employee::getEmpDOB)).toList());

    }

    @PutMapping("/update/{empId}")
    public ResponseEntity<Employee> update(@PathVariable int empId, @RequestBody Employee employee) {
        Employee employee1 = employeeService.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee ID Does Not Exist"));

        employee1.setEmpName(employee.getEmpName());
        employee1.setEmpAddress(employee.getEmpAddress());
        employee1.setEmpSalary(employee.getEmpSalary());
        employee1.setEmpEmailId(employee.getEmpEmailId());
        employee1.setEmpDOB(employee.getEmpDOB());
        employee1.setEmpContactNumber(employee.getEmpContactNumber());
        employee1.setEmpPassword(employee.getEmpPassword());
        employee1.setEmpUID(employee.getEmpUID());

        return ResponseEntity.ok(employeeService.update(employee1));

    }

    @PostMapping("/saveall")
    public ResponseEntity<List<Employee>> saveAll(@RequestBody List<Employee> employeeList) {
        return ResponseEntity.ok(employeeService.saveAll(employeeList));
    }

    @PatchMapping("/changeaddress/{empId}/{empAddress}")
    public ResponseEntity<Employee> changeAddress(@PathVariable int empId, @PathVariable String empAddress) {

        Employee employee = employeeService.findById(empId).orElseThrow(() -> new RecordNotFoundException("Employee ID Does Not Exist"));

        employee.setEmpAddress(empAddress);

        return ResponseEntity.ok(employeeService.update(employee));

    }

    @DeleteMapping("/deletebyid/{empId}")
    public ResponseEntity<String> deleteById(@PathVariable int empId) {
        employeeService.deleteById(empId);
        return ResponseEntity.ok("Data Deleted Successfully");
    }

    @DeleteMapping("/deleteall")
    public ResponseEntity<String> deleteAll() {
        employeeService.deleteAll();
        return ResponseEntity.ok("All Data Deleted Successfully");
    }



}
