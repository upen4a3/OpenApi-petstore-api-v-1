package com.pet.operations.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.operations.category.IntegrationTest;
import com.pet.operations.model.NewPet;
import com.pet.operations.model.Pets;
import com.pet.operations.service.PetOperationsService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
@Category(IntegrationTest.class)
public class PetOperationsControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Spy
	PetOperationsService mockPetOperationsService;

	@InjectMocks
	PetOperationsController petOperationsController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.petOperationsController).build();

	}

	@Test
	public void verify_OnlyOnePet_BasedOnId() throws Exception {

		Pets pet = new Pets();
		pet.setId(1L);
		pet.setName("Tommy");
		pet.setTag("Please Call below Number 91123645789");
		List<Pets> users = new ArrayList<Pets>();
		users.add(pet);
		Mockito.doReturn(pet).when(mockPetOperationsService).getPetBasedOnId(Mockito.anyLong());
		mockMvc.perform(get("/pets/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("id", is(1))).andExpect(jsonPath("name", is(pet.getName())))
				.andExpect(jsonPath("tag", is(pet.getTag())));
		;
		verify(mockPetOperationsService, times(1)).getPetBasedOnId(1L);
		verifyNoMoreInteractions(mockPetOperationsService);

	}

	@Test
	public void getPetWhichIsnotPresent() throws Exception {

		Pets pet = new Pets();
		pet.setId(null);
		pet.setName("Tommy");
		pet.setTag("Please Call below Number 91123645789");
		List<Pets> users = new ArrayList<Pets>();
		users.add(pet);
		Mockito.doReturn(pet).when(mockPetOperationsService).getPetBasedOnId(Mockito.anyLong());
		mockMvc.perform(get("/pets/1")).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
		verify(mockPetOperationsService, times(1)).getPetBasedOnId(1L);
		verifyNoMoreInteractions(mockPetOperationsService);

	}

	@Test
	public void verify_getAllPets() throws Exception {

		Pets pet = new Pets();
		pet.setId(1L);
		pet.setName("Tommy");
		pet.setTag("Please Call below Number 91123645789");
		List<Pets> petList = new ArrayList<Pets>();
		petList.add(pet);
		Mockito.doReturn(petList).when(mockPetOperationsService).getAllPets(Mockito.any(), Mockito.anyLong());
		mockMvc.perform(get("/pets?limit=1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id", is(1)))
				.andExpect(jsonPath("$[0].name", is(pet.getName()))).andExpect(jsonPath("$[0].tag", is(pet.getTag())));

		verify(mockPetOperationsService, times(1)).getAllPets(null, 1L);
		verifyNoMoreInteractions(mockPetOperationsService);

	}

	@Test
	public void verify_createPetInfoForAllPets() throws Exception {
		NewPet newPet = new NewPet();
		newPet.setName("Tommy");
		newPet.setTag("Please Call below Number 91123645789");

		String successMessage = "Inserted successfully";
		Mockito.doReturn(successMessage).when(mockPetOperationsService).createPetInfoForAllPets(Mockito.any());

		mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE).content(asJsonString(newPet))).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

	}

	@Test
	public void verify_deletePetMethod() throws Exception {

		Mockito.doReturn(true).when(mockPetOperationsService).deletePetBasedOnId(Mockito.anyLong());

		mockMvc.perform(delete("/pets/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(mockPetOperationsService, times(1)).deletePetBasedOnId(1L);
		verifyNoMoreInteractions(mockPetOperationsService);

	}

	@Test
	public void verify_deletePetMethodpetNotFound() throws Exception {

		Mockito.doReturn(false).when(mockPetOperationsService).deletePetBasedOnId(Mockito.anyLong());

		mockMvc.perform(delete("/pets/1")).andExpect(status().is4xxClientError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

		verify(mockPetOperationsService, times(1)).deletePetBasedOnId(1L);
		verifyNoMoreInteractions(mockPetOperationsService);

	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
