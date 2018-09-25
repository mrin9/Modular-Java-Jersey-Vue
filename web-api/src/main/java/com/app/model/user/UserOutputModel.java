package com.app.model.user;

import com.app.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.security.Principal;


public class UserOutputModel implements Serializable, Principal {
    private String userId;
    @ApiModelProperty(allowableValues="ADMIN, SUPPORT, CUSTOMER")
    private String role;
    private String fullName;
    private String email;
    private Integer customerId;
    private Integer employeeId;
    private String token;


    //Constructors
    public UserOutputModel(){}

    public UserOutputModel(UserViewModel userView){
        this.setUserId(userView.getUserId());
        this.setRole(userView.getRole());
        this.setFullName(userView.getFullName());
        this.setEmail(userView.getEmail());
        this.setEmployeeId(userView.getEmployeeId());
        this.setCustomerId(userView.getCustomerId());
        this.setToken("");
    }

    public UserOutputModel(String userId, String role, String fullName, String email, Integer empId, Integer custId, String token){
        this.setUserId(userId);
        this.setRole(role);
        this.setFullName(fullName);
        this.setEmail(email);
        this.setCustomerId(custId);
        this.setEmployeeId(empId);
        this.setToken(token);
    }

    //Getters & Setters
    @JsonIgnore // This getter is duplicate of getId but is must for all classes that implements java.security.Principal
    public String getName() {return userId;}


    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public String getToken() {return token; }
    public void setToken(String token) {this.token = token; }

}
