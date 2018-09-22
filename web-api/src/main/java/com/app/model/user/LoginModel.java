package com.app.model.user;

import io.swagger.annotations.*;

@ApiModel(value = "LoginModel", description="Login object")
public class LoginModel {

    @ApiModelProperty(value = "User Name", example = "demo", required=true)
    private String  username;

    @ApiModelProperty(value = "Password", example = "demo",required=true)
    private String  password;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

}