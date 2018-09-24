package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.security.Principal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Transient;


@Entity
public class User implements Serializable, Principal {
    @Id  @Column(name = "user_id") private String userId;
    private String password;
    private String company;
    @Column(name = "first_name") private  String firstName;
    @Column(name = "last_name") private   String lastName;
    private String email;

    @JsonIgnore private String phone;
    @JsonIgnore private String address1;
    @JsonIgnore private String address2;
    @JsonIgnore private String country;
    @JsonIgnore private String postal;

    private String role;


    public User(){}
    /*
    public User(){
        this("new", "PASSWORD", "USER", "new", "new", true, "", "", "", "", "", "", "", "", true, false);
    }
    */

    public User(String userId, String role, String firstName, String lastName){
        this(userId, "", role, firstName, lastName, "", "", "", "", "", "");
    }

    public User(String userId, String password, String role, String firstName, String lastName, String company, String phone, String address1, String address2, String country, String postal){
        this.setUserId(userId);
        this.setEmail(userId);
        this.setPassword(password);
        this.setRole(role);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setCompany(company);
        this.setPhone(phone);
        this.setAddress1(address1);
        this.setAddress2(address2);
        this.setCountry(country);
        this.setPostal(postal);
    }

    @JsonIgnore // This getter is duplicate of getId but is must for all classes that implements java.security.Principal
    public String getName() {return userId;}

    @JsonIgnore @Transient public String getFullName(){ return this.firstName + " " + this.lastName; }

    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPostal() { return postal; }
    public void setPostal(String postal) { this.postal = postal; }

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role; }


}
