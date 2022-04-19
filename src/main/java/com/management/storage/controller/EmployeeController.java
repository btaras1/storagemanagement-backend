package com.management.storage.controller;


import com.management.storage.model.Employee;
import com.management.storage.repository.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    List<Employee> findAll(){return employeeRepository.findAll();}

    @GetMapping("{id}")
    public Employee findById(@PathVariable Long id){return employeeRepository.getById(id);}

    @PostMapping
    public Employee create(@RequestBody final Employee employee){return employeeRepository.saveAndFlush(employee);}

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Employee update(@PathVariable Long id, @RequestBody Employee employee){
        Employee currentEmployee = employeeRepository.getById(id);
        BeanUtils.copyProperties(employee, currentEmployee, "id");
        return employeeRepository.saveAndFlush(currentEmployee);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        employeeRepository.deleteById(id);
    }
}
