package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeService() {
    }

    public Employee saveEmployee(Employee employee) {
        Employee newEmployee = employeeRepository.save(employee);
        return newEmployee;
    }

    public Employee findById(Long employeeId) throws Exception {
        Employee newEmployee = employeeRepository.getOne(employeeId);
        return newEmployee;
    }

    public List<Employee> getEmployeesByService(LocalDate date, Set<EmployeeSkill> skills) {
        List<Employee> employees = employeeRepository
                    .findEmployeeByDaysAvailable(date.getDayOfWeek()).stream()
                    .filter(employee -> employee.getSkills().containsAll(skills))
                    .collect(Collectors.toList());
        return employees;
    }

    public Employee getEmployeeById(Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return employee;
    }

    public void setEmployeeAvailability(Set<DayOfWeek> days, Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(days);
        employeeRepository.save(employee);
    }
}
