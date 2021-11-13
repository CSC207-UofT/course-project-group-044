package com.hr.service;

import com.hr.entity.Employee;

public interface EmployeeModifier {
    Employee hireEmployee(String name, int id, int salary, int maxHoursPerWeek, int hoursPerShift);

    void fireEmployee(Employee employee);

    Employee findEmployeeById(int id);

    double evaluateSalary(int id);
}
