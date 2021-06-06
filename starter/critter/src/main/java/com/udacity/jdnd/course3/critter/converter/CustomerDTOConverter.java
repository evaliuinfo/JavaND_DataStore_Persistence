package com.udacity.jdnd.course3.critter.converter;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerDTOConverter {
    private PetService petService;

    public CustomerDTOConverter(PetService petService) {
        this.petService = petService;
    }

    public CustomerDTO convertCustomerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        List<Long> petIds = new ArrayList<>();
        if (customer.getPets() != null) {
            customer.getPets().forEach(pet -> petIds.add(pet.getId()));
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public Customer convertDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        List<Long> petIds = customerDTO.getPetIds();
        if (petIds != null) {
            List<Pet> pets = petService.getAllPets();
            customer.setPets(pets);
        }
        return customer;
    }

}
