package com.aishpam.esdminiproject.dto;

import com.aishpam.esdminiproject.entity.Employees;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeReqRes {

    private int statusCode;
    private String message;
    private String error;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private String photograph_path;
    private String department;
    private String password;
    private Employees employees;
    private List<Employees> employeesList;


}
