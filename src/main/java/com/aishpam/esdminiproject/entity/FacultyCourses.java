package com.aishpam.esdminiproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Entity
@Table(name = "Faculty_Courses")
public class FacultyCourses {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Setter methods to properly set the employee and course
    @ManyToOne
    @JoinColumn(name = "faculty", nullable = false)
    private Employees employee;

    @Getter
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;


    public Integer getCourseId() {
        return this.course != null ? this.course.getCourseId() : null;
    }

    public Integer getEmployeeId() {
        return this.employee != null ? this.employee.getEmployeeId() : null;
    }

}

