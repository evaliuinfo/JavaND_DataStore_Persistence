package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.converter.CustomerDTOConverter;
import com.udacity.jdnd.course3.critter.converter.EmployeeDTOConverter;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
@Api(tags = {"User"})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final CustomerDTOConverter customerDTOConverter;
    private final EmployeeDTOConverter employeeDTOConverter;

    public UserController(CustomerService customerService,
                          EmployeeService employeeService,
                          CustomerDTOConverter customerDTOConverter,
                          EmployeeDTOConverter employeeDTOConverter) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.customerDTOConverter = customerDTOConverter;
        this.employeeDTOConverter = employeeDTOConverter;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = customerDTOConverter.convertDTOToCustomer(customerDTO);
        List<Long> petIds = customerDTO.getPetIds();
        return customerDTOConverter.convertCustomerToDTO(customerService.saveCustomer(customer, petIds));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.getAllCustomers()
                .stream()
                .map(customerDTOConverter::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) throws Exception {
        Customer customer = customerService.getCustomerByPetId(petId);
        return customerDTOConverter.convertCustomerToDTO(customer);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOConverter.convertDTOToEmployee(employeeDTO);
        return employeeDTOConverter.convertEmployeeToDTO(employeeService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) throws Exception {
        return employeeDTOConverter.convertEmployeeToDTO(employeeService.findById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        LocalDate localDate = employeeDTO.getDate();
        HashSet<EmployeeSkill> skills = new HashSet<>(employeeDTO.getSkills());

        return employeeService.getEmployeesByService(localDate, skills)
                .stream()
                .map(employeeDTOConverter::convertEmployeeToDTO)
                .collect(Collectors.toList());
    }

}
