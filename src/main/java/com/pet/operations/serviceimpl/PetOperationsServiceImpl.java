package com.pet.operations.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.operations.entity.PetEntity;
import com.pet.operations.model.NewPet;
import com.pet.operations.model.Pets;
import com.pet.operations.repository.PetOperationsRepository;
import com.pet.operations.service.PetOperationsService;

@Service
public class PetOperationsServiceImpl implements PetOperationsService {

	@Autowired
	PetOperationsRepository petOperationsRepository;

	@Override
	public List<Pets> getAllPets(String[] tags, Long limit) {
		List<Pets> pets = new ArrayList<Pets>();
		List<PetEntity> petEntity = petOperationsRepository.findAll();

		pets = getEntityToUpdate(petEntity);
		if (tags != null && tags.length > 0) {
			pets.clear();
			List<String> tagList = Arrays.asList(tags);
			for (PetEntity entity : petEntity) {
				String petTag = entity.getTag();
				for (String s : tagList) {
					boolean valid = Pattern.compile(s).matcher(petTag).find();
					if (valid == true) {
						Pets pet = new Pets();
						pet.setId(entity.getId());
						pet.setName(entity.getName());
						pet.setTag(entity.getTag());
						pets.add(pet);
					}

				}

			}

		}
		if (limit != null && limit > 0) {
			return pets.stream().limit(limit > 0 ? limit : pets.size()).collect(Collectors.toList());
		}
		return pets;
	}

	@Override
	public String createPetInfoForAllPets(NewPet newPet) {
		PetEntity entity = new PetEntity();
		PetEntity petEntity = petOperationsRepository.findByName(newPet.getName());
		if (petEntity != null) {
			entity.setId(petEntity.getId());
			entity.setName(newPet.getName());
			entity.setTag(newPet.getTag());
			entity = petOperationsRepository.save(entity);
		}
		entity.setName(newPet.getName());
		entity.setTag(newPet.getTag());
		entity = petOperationsRepository.save(entity);
		if (entity != null) {
			return "Inserted successfully";
		}
		return "Insertion Failed";
	}

	@Override
	public Pets getPetBasedOnId(Long id) {
		Pets pet = new Pets();
		if (id != null) {
			Optional<PetEntity> petEntity = petOperationsRepository.findById(id);
			if (petEntity.isPresent() == true) {
				pet.setId(petEntity.get().getId());
				pet.setName(petEntity.get().getName());
				pet.setTag(petEntity.get().getTag());

			}
		}
		return pet;

	}

	@Override
	public boolean deletePetBasedOnId(Long id) {
		Optional<PetEntity> petEntity = petOperationsRepository.findById(id);
		if ((petEntity.isPresent() == true)) {
			petOperationsRepository.deleteById(id);
			Optional<PetEntity> petEntity1 = petOperationsRepository.findById(id);
			if ((petEntity1 != null)) {
				{
					return true;
				}
			}
		}
		return false;

	}

	public List<Pets> getEntityToUpdate(List<PetEntity> petEntity) {
		List<Pets> pets = new ArrayList<Pets>();
		for (PetEntity entity : petEntity) {
			Pets pet = new Pets();
			pet.setId(entity.getId());
			pet.setName(entity.getName());
			pet.setTag(entity.getTag());
			pets.add(pet);

		}
		return pets;
	}

}
