package com.luv2code.springboot.cruddemo.rest;

import java.util.List;

import javax.management.RuntimeErrorException;

// import com.luv2code.springboot.cruddemo.dao.EmployeeDAO;
import com.luv2code.springboot.cruddemo.entity.Employee;
import com.luv2code.springboot.cruddemo.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

  /**
   * Quick and dirty - use DAO instead of service layer 
   */
  // private EmployeeDAO employeeDAO;

  // // inject employee dao using constructor injection
  // @Autowired
  // public EmployeeRestController(EmployeeDAO employeeDAO) {
  //   this.employeeDAO = employeeDAO;
  // }

  // // expose "/employees" endpoint which returns list of employees
  // @GetMapping("/employees")
  // public List<Employee> findAll() {
  //   return employeeDAO.findAll();
  // }

  private EmployeeService employeeService;

  @Autowired
  public EmployeeRestController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  // expose "/employees" endpoint which returns list of employees
  @GetMapping("/employees")
  public List<Employee> findAll() {
    return employeeService.findAll();
  }

  @GetMapping("/employees/{employeeId}")
  public Employee getEmployee(@PathVariable int employeeId) {
    Employee employee = employeeService.findById(employeeId);

    if (employee == null) {
      throw new RuntimeException("Employee id not found - " + employeeId);
    }

    return employee;
  }

  @PostMapping("/employees")
  public Employee addEmployee(@RequestBody Employee employee) {
    // set id to 0 in case user passes an id in json - this saves a new item instead of updating
    employee.setId(0);

    employeeService.save(employee);

    return employee;
  }

  @PutMapping("/employees")
  public Employee updateEmployee(@RequestBody Employee employee) {
    employeeService.save(employee);
    return employee;
  }

  @DeleteMapping("/employees/{employeeId}")
  public String deleteEmployee(@PathVariable int employeeId) {
    Employee tempEmployee = employeeService.findById(employeeId);

    if (tempEmployee == null) {
      throw new RuntimeErrorException(null, "Employee id not found - " + employeeId);
    }

    employeeService.deleteById(employeeId);

    return "Deleted employee id - " + employeeId;
  }
}