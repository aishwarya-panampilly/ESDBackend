package com.aishpam.esdminiproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCodeReq {
    private Integer courseId;
    private String courseCode;

    public CourseCodeReq(Integer courseId, String courseCode) {
        this.courseId = courseId;
        this.courseCode = courseCode;
    }
}