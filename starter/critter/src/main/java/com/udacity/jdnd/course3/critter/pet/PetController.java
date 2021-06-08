package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.converter.PetDTOConverter;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
@Api(tags = {"Pet"})
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;
    private final PetDTOConverter petDTOConverter;

    public PetController(PetService petService, CustomerService customerService, PetDTOConverter petDTOConverter) {
        this.petService = petService;
        this.customerService = customerService;
        this.petDTOConverter = petDTOConverter;
    }

    @PostMapping
    @ApiOperation(value = "Creates a pet object")
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Customer customer = customerService.findById(petDTO.getId());
        Pet newPet = petDTOConverter.convertDTOToPet(petDTO);
        newPet.setCustomer(customer);
        return petDTOConverter.convertPetToDTO(petService.savePet(newPet, customer.getId()));
    }

    @GetMapping("/{petId}")
    @ApiOperation(value = "Finds a pet object given its id")
    public PetDTO getPet(@PathVariable long petId) throws Exception{
        Pet pet = petService.getPetById(petId);
        return petDTOConverter.convertPetToDTO(pet);
    }

    @GetMapping
    @ApiOperation(value = "Returns a list of all pets ")
    public List<PetDTO> getPets(){
        return petService.getAllPets()
                .stream()
                .map(petDTOConverter::convertPetToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    @ApiOperation(value = "Returns a list of all pets belonging to an owner given the owner id")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByCustomerId(ownerId)
                .stream()
                .map(petDTOConverter::convertPetToDTO)
                .collect(Collectors.toList());
    }
}
