package com.udacity.jdnd.course3.critter.converter;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PetDTOConverter {
    //public PetDTO convertPetToDTO(Pet pet) {
    //    PetDTO petDTO = new PetDTO();
    //    BeanUtils.copyProperties(pet, petDTO);
    //    petDTO.setOwnerId(pet.getCustomer().getId());
    //    return petDTO;
    //}

    public PetDTO convertPetToDTO(Pet pet) {
        return new PetDTO(pet.getId(), pet.getType(), pet.getName(), pet.getCustomer().getId(), pet.getBirthDate(), pet.getNotes());
    }

    public Pet convertDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
