package com.app.model.user;


import io.swagger.annotations.ApiModelProperty;

public class UserRegistrationModel {

    @ApiModelProperty(example = "mickey", required=true) private String userId;
    @ApiModelProperty(example = "mickey", required=true) private String password;
    @ApiModelProperty(example = "CUSTOMER", allowableValues="ADMIN, SUPPORT, CUSTOMER", required=true) private String role;
    @ApiModelProperty(example = "Mouse" , required=true) private String lastName;
    @ApiModelProperty(example = "Mickey", required=true) private String firstName;
    @ApiModelProperty(example = "mmouse@example.com")    private String email;
    @ApiModelProperty(example = "Desney")                private String company;
    @ApiModelProperty(example = "800-200-3490")          private String phone;
    @ApiModelProperty(example = "1313 Disneyland Dr")    private String address1;
    @ApiModelProperty(example = "Adventure Park")        private String address2;
    @ApiModelProperty(example = "Anaheim")               private String city;
    @ApiModelProperty(example = "CA")                    private String state;
    @ApiModelProperty(example = "92802")                 private String postalCode;
    @ApiModelProperty(example = "USA")                   private String country;
    @ApiModelProperty(value="applicable for ROLES - ADMIN and SUPPORT only ", example = "Sales") private String department;
    @ApiModelProperty(value="applicable for ROLES - ADMIN and SUPPORT only ", example = "201")   private Integer managerId;

    //Getters and Setters
    public String getUserId() {return userId;}
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

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public Integer getManagerId() { return managerId; }
    public void setManagerId(Integer managerId) { this.managerId = managerId; }
}
