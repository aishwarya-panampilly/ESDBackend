package com.aishpam.esdminiproject.repository;



import com.aishpam.esdminiproject.entity.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepo extends JpaRepository<Courses, Integer> {
}
