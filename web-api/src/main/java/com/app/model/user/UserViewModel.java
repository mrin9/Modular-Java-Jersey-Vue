package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Immutable;
import javax.persistence.*;
import java.io.Serializable;
import java.security.Principal;

@Entity
@Immutable //Indicates its a View not a table (cannot be updated)
@Table(name = "user_view")
public class UserViewModel implements Serializable, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")  private String userId;
    @Column(name = "password") private String password;
    @Schema(allowableValues="ADMIN, SUPPORT, CUSTOMER") private String role;
    @Column(name = "employee_id") private Integer employeeId;
    @Column(name = "customer_id") private Integer customerId;
    @Column(name = "full_name")   private String fullName;
    @Column(name = "email")       private String email;

    public UserViewModel(){}

    public UserViewModel(String userId, String role, String fullName, String email, Integer empId, Integer custId){
        this.setUserId(userId);
        this.setRole(role);
        this.setFullName(fullName);
        this.setEmail(email);
        this.setEmployeeId(empId);
        this.setCustomerId(custId);
    }

    @JsonIgnore // This getter is duplicate of getId but is must for all classes that implements java.security.Principal
    public String getName() {return userId;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
