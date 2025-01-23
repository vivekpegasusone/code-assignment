package com.swissre;

import com.swissre.model.Employee;
import com.swissre.reader.CsvReader;
import com.swissre.report.Report;
import com.swissre.report.ReportGenerator;
import com.swissre.util.Utility;

import java.io.File;
import java.util.List;
import java.util.Map;

public class App {
    public static void main( String[] args ) throws Exception {
        if(args.length == 0) {
            throw new Exception("Please provide csv file name. Default delimiter is comma(',')");
        }

        File fileName = new File(args[0]);
        if(fileName.exists() && fileName.isFile()) {
            List<Employee> employeeList = loadEmployRecord(fileName);
            Map<Employee, List<Employee>> managerReporteeMap = Utility.getManagerReporteeMap(employeeList);
            List<Report> lessPaidManagers = ReportGenerator.getLessPaidManagers(managerReporteeMap);
            List<Report> higherPaidManagers = ReportGenerator.getHigherPaidManagers(managerReporteeMap);
            List<List<Employee>> reportingLine = ReportGenerator.getLongReportingLine(employeeList);

            System.out.print("*************************************** Less Paid Managers ***************************************\n");
            printReport(lessPaidManagers);
            System.out.print("*************************************** High Paid Managers ***************************************\n");
            printReport(higherPaidManagers);
            System.out.print("*************************************** Reporting Line > 4 ***************************************\n");
            printReportingLine(reportingLine);
        }
    }

    private static void printReportingLine(List<List<Employee>> reportingLine) {
        reportingLine.forEach(x -> {
            Employee emp = x.stream().findFirst().get();
            System.out.println(emp);
        });
    }

    private static void printReport(List<Report> report) {
        report.forEach(System.out::println);
    }

    private static List<Employee> loadEmployRecord(File fileName) throws Exception {
        CsvReader reader = new CsvReader(fileName);
        return reader.readCsv();
        //employees.forEach(System.out::println);
        //return employees;
    }
}
