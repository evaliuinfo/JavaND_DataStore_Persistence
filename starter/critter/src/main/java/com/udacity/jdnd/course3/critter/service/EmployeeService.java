package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findById(Long employeeId) throws Exception {
        return employeeRepository.findById(employeeId).orElseThrow(() ->
                new Exception("Employee with ID "+employeeId+ " not found"));
    }
    public void setAvailability(Set<DayOfWeek> availability, Long employeeId) throws Exception{
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(() ->
                new Exception("Employee with ID "+employeeId+ " not found"));
        employee.setDaysAvailable(availability);
    }

    public List<Employee> findEmployeesForService(LocalDate localDate, HashSet<EmployeeSkill> skills){
        List<Employee> employeesWithSkills = new ArrayList<>();
        List<Employee> availableEmployees = employeeRepository.findEmployeeByDaysAvailable(localDate.getDayOfWeek());
        availableEmployees.forEach(employee -> {
            boolean matchedSkillSet = employee.getSkills().containsAll(skills);
            if (matchedSkillSet) {
                employeesWithSkills.add(employee);
            }
        });
        return employeesWithSkills;
    }
    public List<Employee> list(){
        return employeeRepository.findAll();
    }
}
