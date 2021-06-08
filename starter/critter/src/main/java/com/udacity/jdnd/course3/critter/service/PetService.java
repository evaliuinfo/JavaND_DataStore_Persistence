package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository, CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public List<Pet> list() {
        return petRepository.findAll();
    }

    public Pet save(Pet pet) {
        Pet petToSave = petRepository.save(pet);
        Customer owner = petToSave.getCustomer();
        owner.addPet(petToSave);
        customerRepository.save(owner);
        return petToSave;
    }

    public Pet findById(Long id) throws Exception {
        return petRepository.findById(id).orElseThrow(()-> new Exception("Pet with ID "+id+ " not found"));
    }

    public List<Pet> findByOwnerId(Long ownerId) {
        return petRepository.findPetByCustomerId(ownerId);
    }
}
