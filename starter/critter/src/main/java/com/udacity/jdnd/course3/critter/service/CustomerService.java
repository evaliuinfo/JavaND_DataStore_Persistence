package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface CustomerService {
    List<Customer> list();
    Customer save(Customer customer);
    Customer findOwnerByPet(Pet pet) throws Exception;
    Customer findById(Long id);
}
