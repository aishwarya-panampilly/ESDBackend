package com.aishpam.esdminiproject.service;

import com.aishpam.esdminiproject.dto.CourseReqRes;
import com.aishpam.esdminiproject.dto.CourseDisplay;
import com.aishpam.esdminiproject.dto.EmployeeReqRes;
import com.aishpam.esdminiproject.entity.Courses;
import com.aishpam.esdminiproject.entity.Employees;
import com.aishpam.esdminiproject.entity.FacultyCourses;
import com.aishpam.esdminiproject.exception.BadRequestException;
import com.aishpam.esdminiproject.exception.ResourceNotFoundException;
import com.aishpam.esdminiproject.helper.JWTUtils;
import com.aishpam.esdminiproject.repository.CoursesRepo;
import com.aishpam.esdminiproject.repository.EmployeeRepo;
import com.aishpam.esdminiproject.repository.FacultyCoursesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeManagementService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public EmployeeReqRes login(EmployeeReqRes loginRequest) {
        EmployeeReqRes response = new EmployeeReqRes();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword()));
            // Fetch user details from repository
            Employees user = employeeRepo.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Generate JWT and refresh token
            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setTitle(user.getTitle());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public EmployeeReqRes refreshToken(EmployeeReqRes refreshTokenRequest) {
        EmployeeReqRes response = new EmployeeReqRes();
        try {

            String userEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());

            // Validate token and fetch user
            Employees employees = employeeRepo.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), employees)) {
                var jwt = jwtUtils.generateToken(employees);
                response.setStatusCode(200);
                response.setToken(jwt);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hr");
                response.setMessage("Successfully Refreshed Token");
            }
            response.setStatusCode(200);
            return response;

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage(e.getMessage());
            return response;
        }
    }

    public EmployeeReqRes getAllEmployees() {
        EmployeeReqRes reqRes = new EmployeeReqRes();

        try {
            List<Employees> result = employeeRepo.findAll();
            result.forEach(employee -> {
                // Generate the photo URL for each employee
                String photoUrl = "http://localhost:8080/images/" + employee.getPhotographPath();
                employee.setPhotographPath(photoUrl); // Assuming photographPath is a field in Employees entity
            });
            if (!result.isEmpty()) {
                reqRes.setEmployeesList(result);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No users found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    public EmployeeReqRes getEmployeeById(Integer id) {
        EmployeeReqRes reqRes = new EmployeeReqRes();
        try {
            Employees employee = employeeRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            String photoUrl = "http://localhost:8080/images/" + employee.getPhotographPath();
            employee.setPhotographPath(photoUrl); // Assuming photographPath is a field in Employees entity
            reqRes.setEmployees(employee);
            reqRes.setStatusCode(200);
            reqRes.setMessage("Employees with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public EmployeeReqRes updateEmployee(Integer employeeId, Employees updatedEmployee) {
        EmployeeReqRes reqRes = new EmployeeReqRes();
        try {
            if (updatedEmployee.getEmail() == null || updatedEmployee.getEmail().isEmpty()) {
                throw new BadRequestException("Email is required");
            }
            Employees existingUser = employeeRepo.findByEmployeeId(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            existingUser.setEmployeeRefId(updatedEmployee.getEmployeeRefId());
            existingUser.setFirstName(updatedEmployee.getFirstName());
            existingUser.setLastName(updatedEmployee.getLastName());
            existingUser.setEmail(updatedEmployee.getEmail());
            existingUser.setTitle(updatedEmployee.getTitle());
            existingUser.setPhotographPath(updatedEmployee.getPhotographPath());
            existingUser.setDepartment(updatedEmployee.getDepartment());

            // Check if password is present in the request
            if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
                // Encode the password and update it
                existingUser.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
            }

            Employees savedUser = employeeRepo.save(existingUser);
            reqRes.setEmployees(savedUser);
            reqRes.setStatusCode(200);
            reqRes.setMessage("User updated successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }

    public EmployeeReqRes getMyInfo(String email) {
        EmployeeReqRes reqRes = new EmployeeReqRes();
        try {
            Employees employees = employeeRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            reqRes.setEmployees(employees);
            reqRes.setStatusCode(200);
            reqRes.setMessage("successful");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;
    }

    @Autowired
    private FacultyCoursesRepository facultyCoursesRepository;

    public List<CourseDisplay> getCoursesByEmployeeId(Integer employeeId) {
        List<FacultyCourses> facultyCourses = facultyCoursesRepository.findByEmployee_EmployeeId(employeeId);
        return facultyCourses.stream()
                .map(fc -> {
                    Courses course = fc.getCourse();
                    return new CourseDisplay(course.getCourseId(), course.getCourseCode());
                })
                .collect(Collectors.toList());
    }

    @Autowired
    private CoursesRepo coursesRepo;

    public CourseReqRes getAllCourses() {
        CourseReqRes reqRes = new CourseReqRes();

        try {
            List<Courses> result = coursesRepo.findAll();  // Fetch all courses from the database
            if (!result.isEmpty()) {
                // Map each course to CourseDisplay and set the response
                List<CourseDisplay> courseDisplays = result.stream()
                        .map(course -> new CourseDisplay(course.getCourseId(), course.getCourseCode()))
                        .toList();

                reqRes.setCourseList(courseDisplays);
                reqRes.setStatusCode(200);
                reqRes.setMessage("Successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("No courses found");
            }
            return reqRes;
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
            return reqRes;
        }
    }

    @Autowired
    private EmployeeRepo employeesRepository;  // Repository to find employee by employeeId

    @Autowired
    private CoursesRepo coursesRepository;  // Repository to find course by courseId

    public  CourseDisplay updateCourseForEmployee(Integer employeeId, String courseCode) {
        // Step 1: Find the employee by ID
        System.out.println("Received employeeId: " + employeeId);
        System.out.println("Received courseCode: " + courseCode);
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Step 2: Find the course by course code
        Courses course = coursesRepository.findByCourseCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Step 3: Update the faculty (employee) in the course
        course.setFaculty(employee); // This sets the employee as the new faculty for the course

        // Step 4: Save the course, this will persist the updated faculty in the course table
        coursesRepository.save(course);

        // Step 5: Update the FacultyCourses table
        Optional<FacultyCourses> existingFacultyCourse = facultyCoursesRepository.findByEmployeeAndCourse(employee, course);
        if (existingFacultyCourse.isPresent()) {
            FacultyCourses facultyCourse = existingFacultyCourse.get();
            facultyCourse.setEmployee(employee); // Ensure the employee is correctly mapped to the course
            facultyCoursesRepository.save(facultyCourse);
            System.out.println("FacultyCourse saved: " + facultyCourse.getId());// Save the updated mapping in FacultyCourses table
        } else {
            // If no entry exists, create a new one
            FacultyCourses newFacultyCourse = new FacultyCourses();
            newFacultyCourse.setEmployee(employee);
            newFacultyCourse.setCourse(course);
            facultyCoursesRepository.save(newFacultyCourse);
            System.out.println("FacultyCourse saved: " + newFacultyCourse.getId());
        }

        return new CourseDisplay(course.getCourseId(), course.getCourseCode());
    }

    public void updateUserPhoto(Integer userId, String fileName) {
        Employees employee = employeeRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employee.setPhotographPath(fileName); // Assuming the `Employee` entity has a `photo` field for the file name
        employeeRepo.save(employee);
    }

}
