package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.converter.ScheduleDTOConverter;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final PetService petService;
    private final ScheduleDTOConverter scheduleDTOConverter;

    public ScheduleController(ScheduleService scheduleService,
                              EmployeeService employeeService,
                              CustomerService customerService,
                              PetService petService,
                              ScheduleDTOConverter scheduleDTOConverter) {
        this.scheduleService = scheduleService;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.petService = petService;
        this.scheduleDTOConverter = scheduleDTOConverter;
    }

    @PostMapping
    @ApiOperation(value = "Creates a schedule object")
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleDTOConverter.convertDTOToSchedule(scheduleDTO);
        return scheduleDTOConverter.convertScheduleToDTO(scheduleService.create(schedule));
    }

    @GetMapping
    @ApiOperation(value = "Returns list of schedule objects")
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.list()
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    @ApiOperation(value = "Returns a list of all schedules for a pet given the pet id")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) throws Exception {
        Pet pet = petService.findById(petId);
        return scheduleService.findScheduleByPet(pet)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    @ApiOperation(value = "Returns a list of all schedules for an employee given the employee id")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) throws Exception {
        Employee employee = employeeService.findById(employeeId);
        return scheduleService.findScheduleByEmployee(employee)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    @ApiOperation(value = "Returns a list of all schedules for a customer given the customer id")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        Customer customer = customerService.findById(customerId);
        return scheduleService.findScheduleByCustomer(customer)
                .stream()
                .map(scheduleDTOConverter::convertScheduleToDTO)
                .collect(Collectors.toList());
    }
}
