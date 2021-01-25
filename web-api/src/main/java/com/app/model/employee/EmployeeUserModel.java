package com.app.model.employee;

import com.app.model.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Immutable;
import javax.persistence.*;
import java.util.List;

@Entity
@Immutable //Indicates its a View not a table (cannot be updated)
@Table(name = "employee_user_view")
public class EmployeeUserModel {
    @Id
    @Column(name = "employee_id") private Integer employeeId;
    @Column(name = "user_id") private String userId;
    @Column(name = "password") private String password;

    @Schema(allowableValues =  {"ADMIN", "SUPPORT", "CUSTOMER"}, example="ADMIN")
    @Column(name = "role") private String role;

    @Column(name = "last_name") private String  lastName;
    @Column(name = "first_name") private String  firstName;
    private String  email;
    private String  avatar;
    @Column(name = "job_title") private String  jobTitle;
    private String  department;
    @Column(name = "manager_id") private Integer managerId;
    private String  phone;
    private String  address1;
    private String  address2;
    private String  city;
    private String  state;
    @Column(name = "postal_code") private String  postalCode;
    private String  country;

    // Getter and Setters
    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password; }

    public String getRole() {return role; }
    public void setRole(String role) {this.role = role; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String jobTitle) { this.jobTitle = jobTitle; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress1() { return address1; }
    public void setAddress1(String address1) { this.address1 = address1; }

    public String getAddress2() { return address2; }
    public void setAddress2(String address2) { this.address2 = address2; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public static class EmployeeUserResponse extends PageResponse {
        private List<EmployeeUserModel> list;

        public List<EmployeeUserModel> getList() {return list; }
        public void setList(List<EmployeeUserModel> list) { this.list = list; }
    }
}
