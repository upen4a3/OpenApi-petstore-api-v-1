package com.pet.operations.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.pet.operations.model.NewPet;
import com.pet.operations.model.Pets;
import com.pet.operations.model.ResponseCodes;
import com.pet.operations.service.PetOperationsService;

@RestController
@EnableWebMvc
public class PetOperationsController {
	@Autowired
	PetOperationsService petOperationsService;

	@GetMapping("/pets")
	public List<Pets> getAllpets(@RequestParam(value = "tags", required = false) String[] tags,
			@RequestParam(value = "limit", required = false) Long limit) {
		List<Pets> numberOfpets = petOperationsService.getAllPets(tags, limit);
		return numberOfpets;
	}

	@GetMapping("/pets/{id}")
	public ResponseEntity<?> getSinglePet(@PathVariable(value = "id") Long id) {

		ResponseCodes responseCodes = new ResponseCodes();

		Pets numberOfpets = petOperationsService.getPetBasedOnId(id);

		if (numberOfpets.getId() != null) {
			return new ResponseEntity<Pets>(numberOfpets, HttpStatus.valueOf(200));

		} else {
			responseCodes.setErrorCode("100032");
			responseCodes.setErrorMessage("Id Not Found");
			return new ResponseEntity<ResponseCodes>(responseCodes, HttpStatus.valueOf(404));
		}

	}

	@PostMapping("/pets")
	public ResponseCodes createPetInfoForAllPets(@RequestBody NewPet newPet) {
		String numberOfpets = petOperationsService.createPetInfoForAllPets(newPet);
		ResponseCodes responseCodes = new ResponseCodes();
		responseCodes.setResponseMessage(numberOfpets);
		return responseCodes;
	}

	@DeleteMapping("/pets/{id}")
	public ResponseEntity<?> deleteSinglePet(@PathVariable(value = "id") Long id) {

		ResponseCodes responseCodes = new ResponseCodes();

		boolean response = petOperationsService.deletePetBasedOnId(id);

		if (response == true) {
			responseCodes.setResponseCode("100010");
			responseCodes.setResponseMessage("Deleted Successfully");

			return new ResponseEntity<ResponseCodes>(responseCodes, HttpStatus.valueOf(200));
		} else if (response == false) {
			responseCodes.setErrorCode("100017");
			responseCodes.setErrorMessage("Id Not Found to Delete");
		}
		return new ResponseEntity<ResponseCodes>(responseCodes, HttpStatus.valueOf(404));
	}

}
