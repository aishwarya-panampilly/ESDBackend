package com.aishpam.esdminiproject.repository;


import com.aishpam.esdminiproject.entity.Courses;
import com.aishpam.esdminiproject.entity.Employees;
import com.aishpam.esdminiproject.entity.FacultyCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyCoursesRepository extends JpaRepository<FacultyCourses, Integer> {
    List<FacultyCourses> findByEmployee_EmployeeId(Integer employeeId);
    // Find the faculty course entry by employeeId and courseId
    Optional<FacultyCourses> findByEmployeeAndCourse(Employees employee, Courses course);

}
