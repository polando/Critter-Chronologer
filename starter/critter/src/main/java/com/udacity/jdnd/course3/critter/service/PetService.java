package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PetService {

    @Autowired
    PetRepository petRepository;


    public Pet savePet(Pet pet){
        return petRepository.save(pet);
    }

    public Pet findPetById(Long id){
        return petRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetByOwner(long ownerId){
        return petRepository.findPetsByOwner_Id(ownerId);
    }
}
