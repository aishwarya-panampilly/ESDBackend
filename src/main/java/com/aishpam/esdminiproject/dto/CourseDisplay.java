package com.aishpam.esdminiproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// Create CourseDTO
public class CourseDisplay {
    // Getters and Setters
    private Integer courseId;
    private String courseCode;

    public CourseDisplay(Integer courseId, String courseCode) {
        this.courseId = courseId;
        this.courseCode = courseCode;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
