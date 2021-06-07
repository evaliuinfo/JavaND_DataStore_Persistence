package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.converter.ScheduleDTOConverter;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
@Api(tags = {"Schedule"})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final PetService petService;
    private final ScheduleDTOConverter scheduleDTOConverter;

    public ScheduleController(ScheduleService scheduleService,
                              PetService petService,
                              ScheduleDTOConverter scheduleDTOConverter) {
        this.scheduleService = scheduleService;
        this.petService = petService;
        this.scheduleDTOConverter = scheduleDTOConverter;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTOConverter.convertDTOToSchedule(scheduleDTO);
        return scheduleDTOConverter.convertScheduleToDTO(scheduleService.create(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules()
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        Pet pet = petService.getPetById(petId);
        return scheduleService.getPetSchedule(petId)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getEmployeeSchedule(employeeId)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getCustomerSchedule(customerId)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }
}
