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
    @ApiOperation(value = "Creates a customer object")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getPhoneNumber(), customerDTO.getNotes());
        List<Long> petIds = customerDTO.getPetIds();
        customer = customerService.saveCustomer(customer, petIds);
        CustomerDTO convertedCustomer = customerDTOConverter.convertCustomerToDTO(customer);
        return convertedCustomer;
    }

    @GetMapping("/customer")
    @ApiOperation(value = "Returns a list of all customers")
    public List<CustomerDTO> getAllCustomers(){
        return customerService.list()
                .stream()
                .map(customerDTOConverter::convertCustomerToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    @ApiOperation(value = "Finds a customer that owns a particular pet given the pet id")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) throws Exception {
        Customer customer = new Customer();
        customer = customerService.findCustomerByPetId(petId);
        return customerDTOConverter.convertCustomerToDTO(customer);
    }

    @PostMapping("/employee")
    @ApiOperation(value = "Creates an employee object")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOConverter.convertDTOToEmployee(employeeDTO);
        return employeeDTOConverter.convertEmployeeToDTO(employeeService.save(employee));
    }

    @PostMapping("/employee/{employeeId}")
    @ApiOperation(value = "Finds an employee given the employee id")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) throws Exception {
        return employeeDTOConverter.convertEmployeeToDTO(employeeService.findById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    @ApiOperation(value = "Sets the days of the week that an employee is available")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) throws Exception {
        employeeService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    @ApiOperation(value = "Returns a list of employees who are available in a particular day")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        LocalDate localDate = employeeDTO.getDate();
        HashSet<EmployeeSkill> skills = new HashSet<>(employeeDTO.getSkills());

        return employeeService.findEmployeesForService(localDate, skills)
                .stream()
                .map(employeeDTOConverter::convertEmployeeToDTO)
                .collect(Collectors.toList());
    }

}
