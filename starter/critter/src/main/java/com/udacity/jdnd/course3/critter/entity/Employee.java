package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;


/**
 * Create Entity for Employee
 */
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue
    private long id;

    @Nationalized
    private String name;

    @ElementCollection
    private Set<EmployeeSkill> skills = new HashSet<>();

    @ElementCollection
    private Set<DayOfWeek> daysAvailable = new HashSet<>();

    public Employee(Long id, String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public Employee() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
