package com.app.model.customer;

import com.app.model.PageResponse;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

@Entity
@Immutable //Indicates its a View not a table (cannot be updated)
@Table(name = "customer_user_view")
public class CustomerUserModel {
    @Id
    @Column(name = "customer_id") private Integer customerId;
    @Column(name = "user_id") private String userId;
    @Column(name = "password") private String password;

    @ApiModelProperty(allowableValues="ADMIN, SUPPORT, CUSTOMER")
    @Column(name = "role") private String role;

    @Column(name = "last_name") private String lastName;
    @Column(name = "first_name") private String firstName;
    private String email;
    private String company;
    private String phone;
    private String address1;
    private String address2;
    private String city;
    private String state;
    @Column(name = "postal_code") private String postalCode;
    private String country;

    //Getters and Setters

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

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

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

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

    public static class CustomerUserResponse extends PageResponse {

        private List<CustomerUserModel> list;

        public List<CustomerUserModel> getList() {return list; }
        public void setList(List<CustomerUserModel> list) { this.list = list; }
    }
}
