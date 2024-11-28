package com.aishpam.esdminiproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Faculty_Courses")
public class FacultyCourses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "faculty", nullable = false)
    private Employees employee;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    public void setCourseId(Integer courseId) {
    }
    public void setEmployee(Employees employee) {
    }
}
