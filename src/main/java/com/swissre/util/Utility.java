package com.swissre.util;

import com.swissre.model.Employee;

import java.util.*;

public class Utility {

    public static Map<Employee, List<Employee>> getManagerReporteeMap(List<Employee> employees) {
        Map<Employee, List<Employee>> managerReporteeMap = null;
        if (!employees.isEmpty()) {
            managerReporteeMap = new HashMap<>();
            for (Employee employee : employees) {
                Employee manager = findManager(employee, employees);
                List<Employee> reporteeList = managerReporteeMap.get(manager);
                if (reporteeList != null) {
                    reporteeList.add(employee);
                } else {
                    reporteeList = new ArrayList<>();
                    if(!employee.equals(manager)) {
                        reporteeList.add(employee);
                    }
                    managerReporteeMap.put(manager, reporteeList);
                }
            }
        }
        return managerReporteeMap;
    }

    private static Employee findManager(Employee employee, List<Employee> employees) {
        if(Objects.isNull(employee.getManagerId())) {
            return employee;
        }
        return employees.stream().filter(e -> employee.getManagerId().equals(e.getId())).findAny().get();
    }
}
