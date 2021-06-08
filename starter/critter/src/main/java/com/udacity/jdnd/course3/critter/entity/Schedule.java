package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;

    @ManyToMany(targetEntity = Employee.class)
    @JoinTable(name = "schedule_employee",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private List<Employee> employee = new LinkedList<>();

    @ManyToMany(targetEntity = Pet.class)
    @JoinTable(name = "schedule_pet",
            joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "pet_id"))
    private List<Pet> pets = new LinkedList<>();

    @ElementCollection
    private Set<EmployeeSkill> activities = new HashSet<>();

    public Schedule(LocalDate date, Set<EmployeeSkill> activities) {
        this.date = date;
        this.activities = activities;
    }

    public Schedule() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
     }

    public void setEmployee(List<Employee> employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployee() {
        return employee;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Pet> getPets() {
        return pets;
    }
}
