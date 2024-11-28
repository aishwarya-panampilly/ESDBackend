package com.aishpam.esdminiproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Courses")
public class Courses {
    // Getters and setters
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @Getter
    @Column(nullable = false, unique = true)
    private String courseCode;

    private String name;
    private String description;
    private Integer year;
    private String term;
    private Double credits;
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private Employees faculty;

}
