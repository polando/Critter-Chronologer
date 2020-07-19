package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getScheduleByPetId(Long petId){
        Pet pet = petService.findPetById(petId);
        return pet.getSchedules();
    }

    public List<Schedule> getSchedulesByEmployeeId(Long employeeId){
        Employee employee = employeeService.findEmployeeById(employeeId);
        return employee.getSchedules();
    }

    public List<Schedule> getSchedulesByCustomerId(Long customerId){
        List<Pet> pets = petService.getPetByOwner(customerId);
        return pets.stream().map(pet -> pet.getSchedules()).flatMap(List::stream).collect(Collectors.toList());
    }
}
