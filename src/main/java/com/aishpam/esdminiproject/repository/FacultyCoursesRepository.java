package com.aishpam.esdminiproject.repository;


import com.aishpam.esdminiproject.entity.FacultyCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyCoursesRepository extends JpaRepository<FacultyCourses, Integer> {
    List<FacultyCourses> findByEmployee_EmployeeId(Integer employeeId);
}
