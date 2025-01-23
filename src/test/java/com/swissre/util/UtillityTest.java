package com.swissre.util;

import com.swissre.model.Employee;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UtillityTest {

    @Test
    void getManagerReporteeMap() {
        // Given
        List<Employee> employeeList = generateEmployeeList();

        // When
        Map<Employee, List<Employee>> employeeMap = Utility.getManagerReporteeMap(employeeList);

        // Then
        assertNotNull(employeeMap);
        assertEquals(2, employeeMap.size());
    }

    private List<Employee> generateEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(1000, "James", "Bond", 5000));
        employeeList.add(new Employee(1001, "James", "Bond", 5000, 1000));
        employeeList.add(new Employee(1002, "James", "Bond", 5000, 1000));
        employeeList.add(new Employee(1003, "James", "Bond", 5000, 1002));
        return employeeList;
    }
}