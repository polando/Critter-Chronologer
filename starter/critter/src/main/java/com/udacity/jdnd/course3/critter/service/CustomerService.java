package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
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
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getCustomerByPet(Long petId){
        Pet pet = petRepository.findById(petId).orElseThrow(EntityNotFoundException::new);
        return pet.getOwner();
    }

    public Customer getCustomerById(Long customerId){
        return customerRepository.findById(customerId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void addPetToCustomer(Customer customer, Pet pet){
        List<Pet> pets = Optional.ofNullable(customer.getPets()).orElse(new ArrayList<>());
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);
    }


}
