package com.pet.operations.serviceimpl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pet.operations.entity.PetEntity;
import com.pet.operations.model.Pets;
import com.pet.operations.repository.PetOperationsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:bootstrap-test.properties")
@Transactional
public class PetOperationsServiceImplTest {

	PetOperationsServiceImpl petOperationsServiceImpl;
	@Spy
	PetOperationsRepository petOperationsRepository;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		petOperationsServiceImpl = new PetOperationsServiceImpl();
		try {
			FieldUtils.writeField(petOperationsServiceImpl, "petOperationsRepository", petOperationsRepository, true);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void verifyGetAllPetsMethod() {
		PetEntity pet = new PetEntity();
		pet.setId(1L);
		pet.setName("Tommy");
		pet.setTag("Please Call below Number 91123645789");
		PetEntity pet2 = new PetEntity();
		pet2.setId(2L);
		pet2.setName("Damar");
		pet2.setTag("Please Call below Number 91123645789");
		List<PetEntity> listOfPets = new ArrayList<PetEntity>();
		listOfPets.add(pet);
		listOfPets.add(pet2);
		Mockito.doReturn(listOfPets).when(petOperationsRepository).findAll();
		List<Pets> petList = petOperationsServiceImpl.getAllPets(null, null);
		assertEquals(2, petList.size());
		assertEquals(petList.get(0).getId(), Long.valueOf(1));
		assertEquals(petList.get(0).getName(), String.valueOf("Tommy"));

	}

	@Test
	public void verifyGetAllPetsMethodWithTagsAndlimt() {
		PetEntity pet = new PetEntity();
		pet.setId(1L);
		pet.setName("Tommy");
		pet.setTag("Please Call below Number 91123645789");
		PetEntity pet2 = new PetEntity();
		pet2.setId(2L);
		pet2.setName("Damar");
		pet2.setTag("Please Call below Number 91123645789");
		List<PetEntity> listOfPets = new ArrayList<PetEntity>();
		listOfPets.add(pet);
		listOfPets.add(pet2);
		Mockito.doReturn(listOfPets).when(petOperationsRepository).findAll();
		String[] arr = { "Please Call below Number 91123645789" };
		List<Pets> petList = petOperationsServiceImpl.getAllPets(arr, 1L);
		assertEquals(1, petList.size());
		assertEquals(petList.get(0).getId(), Long.valueOf(1));
		assertEquals(petList.get(0).getName(), String.valueOf("Tommy"));

	}

	@Test
	public void verifySingleGetMethod() {
		PetEntity pet = new PetEntity();
		pet.setId(1L);
		pet.setName("Tommy");
		pet.setTag("Please Call below Number 91123645789");

		Mockito.doReturn(Optional.of(pet)).when(petOperationsRepository).findById(Mockito.anyLong());
		Pets onePet = petOperationsServiceImpl.getPetBasedOnId(1L);

		assertEquals(onePet.getId(), Long.valueOf(1));
		assertEquals(onePet.getName(), String.valueOf("Tommy"));
		assertEquals(onePet.getTag(), String.valueOf("Please Call below Number 91123645789"));

	}

}
