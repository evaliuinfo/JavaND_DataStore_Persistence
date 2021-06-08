package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule create(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPet(Pet pet) {
        return scheduleRepository.findScheduleByPets(pet);
    }

    public List<Schedule> findScheduleByEmployee(Employee employee) {
        return scheduleRepository.findScheduleByEmployee(employee);
    }

    public List<Schedule> findScheduleByCustomer(Customer customer) {
        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new LinkedList<>();

        pets.forEach(pet -> {
            List<Schedule> petsOnSchedule = scheduleRepository.findScheduleByPets(pet);
            schedules.addAll(petsOnSchedule);
        });
        return schedules;
    }
}
