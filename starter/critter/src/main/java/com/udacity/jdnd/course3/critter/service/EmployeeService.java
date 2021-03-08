package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Employee findEmployeeById(Long id){
        return employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void setAvailableDays(Set<DayOfWeek> daysAvailable, Long id){
        employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new)
                .setDaysAvailable(daysAvailable);
    }

    public List<Employee> getEmployeeByDetails(LocalDate date, Set<EmployeeSkill> skills){
        List<Employee> employees = employeeRepository.findAllBySkillsIn(skills);
        return employees.stream().filter(employee -> employee.getSkills().containsAll(skills))
                .collect(Collectors.toList());
    }

    public void addScheduleToEmployee(Employee employee, Schedule schedule) {
        List<Schedule> schedules = Optional.ofNullable(employee.getSchedules()).orElse(new ArrayList<>());
        schedules.add(schedule);
        employee.setSchedules(schedules);
        employeeRepository.save(employee);
    }
    public void addScheduleToEmployees(List<Employee> employees,Schedule schedule){
        for (Employee employee:employees) {
            addScheduleToEmployee(employee,schedule);
        }
    }

}
