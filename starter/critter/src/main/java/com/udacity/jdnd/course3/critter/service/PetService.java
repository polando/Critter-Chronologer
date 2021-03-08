package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerService customerService;

    @Autowired
    ScheduleService scheduleService;



    public Pet savePet(Pet pet){
        petRepository.save(pet);
        if(pet.getOwner() != null)
            customerService.addPetToCustomer(pet.getOwner(),pet);
        if(pet.getSchedules() != null)
            scheduleService.addPetToSchedules(pet.getSchedules(),pet);
        return pet;
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

    public void addScheduleToPet(Pet pet, Schedule schedule) {
        List<Schedule> schedules = Optional.ofNullable(pet.getSchedules()).orElse(new ArrayList<>());
        schedules.add(schedule);
        pet.setSchedules(schedules);
        petRepository.save(pet);
    }

    public void addScheduleToPets(List<Pet> pets,Schedule schedule){
        for (Pet pet: pets) {
            addScheduleToPet(pet,schedule);
        }
    }

}
