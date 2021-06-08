package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface PetService {
    List<Pet> list();
    Pet save(Pet pet);
    Pet findById(Long id) throws Exception;
    List<Pet> findByOwnerId(Long ownerId);
}
