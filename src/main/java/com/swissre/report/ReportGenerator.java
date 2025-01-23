package com.swissre.report;

import com.swissre.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReportGenerator {

    //Board wants to make sure that every manager earns
    //at least 20% more than the average salary of its direct subordinates
    public static List<Report> getLessPaidManagers(Map<Employee, List<Employee>> employeeMap) {
        List<Report> report = new ArrayList<>();
        for(Map.Entry<Employee, List<Employee>> entry : employeeMap.entrySet()) {
            Employee manager = entry.getKey();
            List<Employee> employees = entry.getValue();

            double averageSalary = findAverageSalary(employees);
            double minimumManagerSalary = averageSalary * 1.2;

            if(manager.getSalary() < minimumManagerSalary) {
                String message = String.format("%s is less paid. His salary is %d. He is less paid by Rs. %.2f.",
                        manager.getName(), manager.getSalary(), (minimumManagerSalary - manager.getSalary()));
                report.add(new Report(message));
            }
        }
        return report;
    }

    //Board wants to make sure that every manager earns
    //no more than 50% of the average salary of its direct subordinates
    public static List<Report> getHigherPaidManagers(Map<Employee, List<Employee>> employeeMap) {
        List<Report> report = new ArrayList<>();
        for(Map.Entry<Employee, List<Employee>> entry : employeeMap.entrySet()) {
            Employee manager = entry.getKey();
            List<Employee> employees = entry.getValue();

            double averageSalary = findAverageSalary(employees);
            double maxManagerSalary = averageSalary * 1.5;

            if(manager.getSalary() > maxManagerSalary) {
                String message = String.format("%s is higher paid. His salary is %d. He is paid more by Rs. %.2f.",
                        manager.getName(), manager.getSalary(), (manager.getSalary() - maxManagerSalary));
                report.add(new Report(message));
            }
        }
        return report;
    }

    //identify all employees which have more than 4 managers between them and the CEO.
    public static List<List<Employee>> getLongReportingLine(List<Employee> employeeList) {
        List<List<Employee>> empReportingLine = getEmployeeReportingLines(employeeList);
        return empReportingLine.stream().filter(l -> l.size() > 5).collect(Collectors.toList());
    }

    private static List<List<Employee>> getEmployeeReportingLines(List<Employee> employeeList) {
        List<List<Employee>> empReportingLine = new ArrayList<>();

        for(Employee employee : employeeList) {
            if(Objects.nonNull(employee.getManagerId())) { // No need to consider CEO in hierarchy
                List<Employee> employeeHierarchy = findHierarchy(employee, employeeList);
                empReportingLine.add(employeeHierarchy);
            }
        }

        return empReportingLine;
    }

    private static List<Employee> findHierarchy(final Employee employee, List<Employee> employeeList) {
        List<Employee> empChain = new ArrayList<>();
        for(Employee emp : employeeList) {
            if (emp.getId().equals(employee.getManagerId())) {
                findManager(emp, employeeList, empChain);
                break;
            }
        }
        empChain.add(0, employee);
        return empChain;
    }

    private static void findManager(Employee employee, List<Employee> employeeList, List<Employee> empChain) {
        if (employee.getManagerId() == null) {
            empChain.add(employee);
            return;
        }

        Employee manager = employeeList.stream().filter(e -> e.getId().equals(employee.getManagerId())).findFirst().get();
        empChain.add(employee);
        findManager(manager, employeeList, empChain);
    }

    public static double findAverageSalary(List<Employee> employees) {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .average().getAsDouble();
    }
}
//which managers earn less than they should, and by how much
//which managers earn more than they should, and by how much
//which employees have a reporting line which is too long, and by how much