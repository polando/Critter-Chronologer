package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository  extends JpaRepository <Employee,Long> {

    @Query("select e from Employee e where :date member of e.daysAvailable and e.skills = :skills")
    List<Employee> findEmployeesByDetails(LocalDate date, Set<EmployeeSkill> skills);
}
