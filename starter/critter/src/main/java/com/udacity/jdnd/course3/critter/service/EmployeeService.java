package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public interface EmployeeService {
    Employee save(Employee employee);
    Employee findById(Long employeeId) throws Exception;
    void setAvailability(Set<DayOfWeek> availability, Long employeeId) throws Exception;
    Set<Employee> findEmployeesForService(LocalDate localDate, HashSet<EmployeeSkill> skills);
    List<Employee> list();
}
