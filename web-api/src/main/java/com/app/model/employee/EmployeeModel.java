package com.app.model.employee;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class EmployeeModel {

    @Id @GeneratedValue private Integer id;
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

    //Constructors
    public EmployeeModel(){}
    public EmployeeModel(String lastName, String firstName, String email, String avatar, String jobTitle, String department, Integer managerId, String phone, String address1, String address2, String city, String state, String postalCode, String country) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.avatar = avatar;
        this.jobTitle = jobTitle;
        this.department = department;
        this.managerId = managerId;
        this.phone = phone;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }


    // Getter and Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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
}
