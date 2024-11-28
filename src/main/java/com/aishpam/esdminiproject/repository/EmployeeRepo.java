package com.aishpam.esdminiproject.repository;

import com.aishpam.esdminiproject.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employees,Integer> {
    Optional<Employees> findByEmail(String email);
    Optional<Employees> findByEmployeeId(Integer employeeId);
}
