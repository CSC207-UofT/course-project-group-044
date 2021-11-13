package com.hr.entity;

import java.util.List;
import java.util.HashMap;

public class Organization {
    private HashMap<Integer,Employee> employees;

    public Organization(){
        this.employees = new HashMap<Integer, Employee>();
    }

    public Organization(List<Employee> employees) {
        this.employees = new HashMap<Integer, Employee>();
        for (Employee e: employees){
            this.employees.put(e.getId(), e);
        }
    }

    public HashMap<Integer, Employee> getEmployees() {
        return this.employees;
    }

    public void addEmployee(Employee employee) {
        this.employees.put(employee.getId(), employee);
    }


    public void removeEmployee(Employee employee) {
        this.employees.remove(employee.getId());
    }
}
