package com.swissre.model;

public class Employee {
    private final Integer id;
    private final String firstName;
    private final String lastName;
    private final Integer salary;
    private final Integer managerId;

    public Employee(Integer id, String firstName, String lastName, Integer salary) {
        this(id, firstName, lastName, salary, null);
    }

    public Employee(Integer id, String firstName, String lastName, Integer salary, Integer managerId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.managerId = managerId;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public String getName() {
        return new StringBuilder(this.firstName).append(" ").append(this.lastName).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee employee)) return false;

        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                ", managerId=" + managerId +
                '}';
    }
}
