package com.app.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.security.Principal;
import javax.persistence.*;


@Entity
public class User implements Serializable, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id") private String userId;
    private String password;
    private String role;
    @Column(name = "employee_id")   private Integer employeeId;
    @Column(name = "customer_id")   private Integer customerId;

    public User(){}

    public User(String userId, String role){
        this(userId, "", role, null, null);
    }

    public User(String userId, String password, String role, Integer employeeId, Integer customerId){
        this.setUserId(userId);
        this.setPassword(password);
        this.setRole(role);
        if (employeeId !=null) {
            this.setEmployeeId(employeeId);
        }
        if (customerId !=null) {
            this.setCustomerId(customerId);
        }
    }

    @JsonIgnore // This getter is duplicate of getId but is must for all classes that implements java.security.Principal
    public String getName() {return userId;}

    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password; }

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

}
