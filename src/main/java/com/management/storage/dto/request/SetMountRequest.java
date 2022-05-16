package com.management.storage.dto.request;

import com.management.storage.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetMountRequest {
    private List<Employee> employees;
    private String description;
}
