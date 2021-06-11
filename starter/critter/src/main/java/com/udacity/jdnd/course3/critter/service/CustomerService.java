package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public List<Customer> list() {
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer, List<Long> petIds) {
        List<Pet> customerPets = new ArrayList<>();
        if (petIds != null && !petIds.isEmpty()) {
            customerPets = petIds.stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(customerPets);
        return customerRepository.save(customer);
    }

    public Customer findByCustomerId(Long id) {
        return customerRepository.getOne(id);
    }

    public Customer findCustomerByPetId(Long petId) {
        Customer customer = petRepository.getOne(petId).getCustomer();
        return customer;
    }
}
