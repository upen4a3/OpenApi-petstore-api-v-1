package com.pet.operations.service;

import java.util.List;

import com.pet.operations.model.NewPet;
import com.pet.operations.model.Pets;

public interface PetOperationsService {

	List<Pets> getAllPets(String[] tags, Long limit);

	String createPetInfoForAllPets(NewPet newPet);

	Pets getPetBasedOnId(Long id);

	boolean deletePetBasedOnId(Long id);

}
