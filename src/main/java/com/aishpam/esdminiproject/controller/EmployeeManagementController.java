package com.aishpam.esdminiproject.controller;



import com.aishpam.esdminiproject.dto.CourseDisplay;
import com.aishpam.esdminiproject.dto.CourseReqRes;
import com.aishpam.esdminiproject.dto.EmployeeReqRes;
import com.aishpam.esdminiproject.entity.Courses;
import com.aishpam.esdminiproject.entity.Employees;
import com.aishpam.esdminiproject.entity.FacultyCourses;
import com.aishpam.esdminiproject.repository.CoursesRepo;
import com.aishpam.esdminiproject.repository.EmployeeRepo;
import com.aishpam.esdminiproject.repository.FacultyCoursesRepository;
import com.aishpam.esdminiproject.service.EmployeeDetailsService;
import com.aishpam.esdminiproject.service.EmployeeManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeManagementController {
    @Autowired
    private EmployeeManagementService employeeManagementService;

    @PostMapping("/auth/login")
    public ResponseEntity<EmployeeReqRes> login(@RequestBody EmployeeReqRes req) {
        return ResponseEntity.ok(employeeManagementService.login(req));
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<EmployeeReqRes> refreshToken(@RequestBody EmployeeReqRes req) {
        return ResponseEntity.ok(employeeManagementService.refreshToken(req));
    }

    @GetMapping("/auth/get-all-users")
    public ResponseEntity<EmployeeReqRes> getAllUsers() {
        return ResponseEntity.ok(employeeManagementService.getAllEmployees());

    }

    @GetMapping("/auth/get-users/{userId}")
    public ResponseEntity<EmployeeReqRes> getUSerByID(@PathVariable Integer userId) {
        return ResponseEntity.ok(employeeManagementService.getEmployeeById((userId)));

    }

    @PutMapping("/auth/update/{userId}")
    public ResponseEntity<EmployeeReqRes> updateUser(@PathVariable Integer userId, @RequestBody Employees request) {
        return ResponseEntity.ok(employeeManagementService.updateEmployee(userId, request));
    }

    @GetMapping("/auth/get-profile")
    public ResponseEntity<EmployeeReqRes> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        EmployeeReqRes response = employeeManagementService.getMyInfo(email);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @Autowired
    private EmployeeManagementService employeeService;

    @GetMapping("auth/{employeeId}/courses")
    public ResponseEntity<List<CourseDisplay>> getCourses(@PathVariable Integer employeeId) {
        List<CourseDisplay> courses = employeeService.getCoursesByEmployeeId(employeeId);
        return ResponseEntity.ok(courses);
    }

    @Autowired
    private CoursesRepo coursesRepo;

    @GetMapping("/auth/courses")
    public ResponseEntity<CourseReqRes> getAllCourses() {
        return ResponseEntity.ok(employeeManagementService.getAllCourses());// Ensure this returns all courses
    }

    // Endpoint to update a list of courses for a specific employee
    @PutMapping("/employee/{employeeId}/course/{courseId}")
    public ResponseEntity<FacultyCourses> updateCourseForEmployee(
            @PathVariable Integer employeeId,
            @PathVariable Integer courseId,
            @RequestBody FacultyCourses updatedCourse) {

        // Call the service to update the course for the employee
        FacultyCourses updatedFacultyCourse = employeeManagementService.updateCourseForEmployee(employeeId, courseId, updatedCourse);

        // Return the updated FacultyCourses entity as response
        return ResponseEntity.ok(updatedFacultyCourse);
    }
}