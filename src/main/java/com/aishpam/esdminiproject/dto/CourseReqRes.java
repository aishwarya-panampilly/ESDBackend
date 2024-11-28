package com.aishpam.esdminiproject.dto;

import java.util.List;

public class CourseReqRes {
    private List<CourseDisplay> courseList;
    private int statusCode;
    private String message;

    // Getters and Setters
    public List<CourseDisplay> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseDisplay> courseList) {
        this.courseList = courseList;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

