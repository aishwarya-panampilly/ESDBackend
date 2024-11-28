package com.aishpam.esdminiproject.repository;



import com.aishpam.esdminiproject.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepo extends JpaRepository<Courses, Integer> {
    Optional<Courses> findByCourseCode(String courseCode);
}
