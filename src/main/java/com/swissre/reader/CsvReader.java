package com.swissre.reader;

import com.swissre.model.Employee;

import java.io.File;
import java.util.*;

public class CsvReader {
    private final String delimiter = ",";
    private final File fileName;

    public CsvReader(File fileName) {
        this.fileName = fileName;
    }

    public List<Employee> readCsv() throws Exception {
        List<Employee> employeeList = new ArrayList<>();
        try (Scanner scanner = new Scanner(this.fileName)) {
            scanner.useDelimiter(this.delimiter);
            while (scanner.hasNextLine()) {
                String record = scanner.nextLine();
                if (record.startsWith("Id")) {
                    continue;
                }
                getRecordFromLine(record).ifPresent(employeeList::add);
            }
        }
        return employeeList;
    }

    private Optional<Employee> getRecordFromLine(String employeeRecord) {
        String[] empRec = employeeRecord.split(this.delimiter);
        if(empRec.length < 4) {
            System.out.println("Incorrect employee record. " + employeeRecord);
            return Optional.empty();
        }
        Integer id = !empRec[0].trim().isEmpty() ? Integer.parseInt(empRec[0].trim()) : null;
        String firstName = empRec[1].trim();
        String lastName = empRec[2].trim();
        Integer salary = !empRec[3].trim().isEmpty() ? Integer.parseInt(empRec[3].trim()) : null;
        Integer managerId = (empRec.length > 4 && !empRec[4].trim().isEmpty()) ? Integer.parseInt(empRec[4].trim()) : null;

        if (Objects.isNull(id) || Objects.isNull(salary)) {
            System.out.println("Employee id or salary can not be empty and must be numeric. " + employeeRecord);
            return Optional.empty();
        }
        return Optional.of(new Employee(id, firstName, lastName, salary, managerId));
    }
}
