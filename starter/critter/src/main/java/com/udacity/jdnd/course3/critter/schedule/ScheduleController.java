package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Person;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;


    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertScheduleDTOTOSchedule(scheduleDTO);
        return convertScheduleTOScheduleDTO(scheduleService.createSchedule(schedule));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream()
                .map(this::convertScheduleTOScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getScheduleByPetId(petId).stream()
                .map(this::convertScheduleTOScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
            return scheduleService.getSchedulesByEmployeeId(employeeId).stream()
                .map(this::convertScheduleTOScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getSchedulesByCustomerId(customerId).stream()
                .map(this::convertScheduleTOScheduleDTO)
                .collect(Collectors.toList());
    }

    Schedule convertScheduleDTOTOSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);
        schedule.setPets(scheduleDTO.getPetIds()
                .stream().map(id->petService.findPetById(id))
                .collect(Collectors.toList()));
        schedule.setEmployees(scheduleDTO.getEmployeeIds()
                .stream().map(id->employeeService.findEmployeeById(id))
                .collect(Collectors.toList()));
        return schedule;
    }

    ScheduleDTO convertScheduleTOScheduleDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        scheduleDTO.setEmployeeIds(schedule.getEmployees()
                .stream().map(Person::getId)
                .collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets()
                .stream().map(Pet::getId)
                .collect(Collectors.toList())
        );
    return scheduleDTO;
    }
}
