package com.management.storage.controller;


import com.management.storage.model.Employee;
import com.management.storage.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EmployeeController {

    EmployeeService employeeService;

    @GetMapping
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("{id}")
    public Employee findById(@PathVariable final Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public Employee create(@Valid @RequestBody final Employee employee) {
        return employeeService.createOrUpdate(employee);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Employee update(@Valid @RequestBody final Employee employee) {
        return employeeService.createOrUpdate(employee);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final Long id) {
        employeeService.deleteById(id);
    }

    @GetMapping("/count")
    public Long count() {
        return employeeService.count();
    }
}
