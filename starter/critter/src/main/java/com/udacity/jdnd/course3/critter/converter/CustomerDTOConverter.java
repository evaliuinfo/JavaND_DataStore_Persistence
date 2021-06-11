package com.udacity.jdnd.course3.critter.converter;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerDTOConverter {
    private PetService petService;

    public CustomerDTOConverter(PetService petService) {
        this.petService = petService;
    }

    public CustomerDTO convertCustomerToDTO(Customer customer) {
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        CustomerDTO customerDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getPhoneNumber(), customer.getNotes(), petIds);
        return customerDTO;
    }

    public Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            List<Pet> pets = petService.list();
            customer.setPets(pets);
        }
        return customer;
    }

}
