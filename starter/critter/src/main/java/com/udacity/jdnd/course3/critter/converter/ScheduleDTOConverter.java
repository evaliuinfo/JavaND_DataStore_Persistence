package com.udacity.jdnd.course3.critter.converter;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleDTOConverter {
    private PetService petService;
    private EmployeeService employeeService;

    public ScheduleDTOConverter(PetService petService, EmployeeService employeeService) {
        this.petService = petService;
        this.employeeService = employeeService;
    }

    public ScheduleDTO convertScheduleToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> employeeIds = new ArrayList<>();
        List<Long> petIds = new ArrayList<>();

        if (schedule.getEmployee() != null) {
            schedule.getEmployee().forEach(employee -> employeeIds.add(employee.getId()));
        }
        if (schedule.getPets() != null) {
            schedule.getPets().forEach(pet -> petIds.add(pet.getId()));
        }
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);
        return scheduleDTO;
    }

    public Schedule convertDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        List<Long> employeeIds = scheduleDTO.getEmployeeIds();
        List<Long> petIds = scheduleDTO.getPetIds();

        List<Employee> employees = new ArrayList<>();
        List<Pet> pets = new ArrayList<>();

        if (employeeIds != null) {
            employeeIds.forEach(employeeId -> {
                try {
                    employees.add(employeeService.findById(employeeId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        if (petIds != null) {
            petIds.forEach(petId -> {
                try {
                    pets.add(petService.findById(petId));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        schedule.setEmployee(employees);
        schedule.setPets(pets);
        return schedule;
    }
}
