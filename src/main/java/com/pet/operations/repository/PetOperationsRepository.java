package com.pet.operations.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pet.operations.entity.PetEntity;

@Repository
@Transactional
public interface PetOperationsRepository extends JpaRepository<PetEntity, Long> {
	List<PetEntity> findAll();

	PetEntity findByName(String name);

}
