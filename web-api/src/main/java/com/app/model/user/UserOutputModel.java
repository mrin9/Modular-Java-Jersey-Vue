package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.security.Principal;


public class UserOutputModel implements Serializable, Principal {
    private String userId;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private String company;
    private String token;

    //Constructors
    public UserOutputModel(){}

    public UserOutputModel(User user){
        this.setUserId(user.getUserId());
        this.setRole(user.getRole());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setEmail(user.getEmail());
        this.setCompany(user.getCompany());
        this.setToken("");
    }

    public UserOutputModel(String userId, String role, String firstName, String lastName, String email, String company, String token){
        this.setUserId(userId);
        this.setRole(role);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setCompany(company);
        this.setToken(token);
    }

    //Getters & Setters
    @JsonIgnore // This getter is duplicate of getId but is must for all classes that implements java.security.Principal
    public String getName() {return userId;}

    public String getFullName(){ return this.firstName + " " + this.lastName; }

    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getToken() {return token; }
    public void setToken(String token) {this.token = token; }

}
