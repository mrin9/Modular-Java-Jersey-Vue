package com.app.model.user;

import com.app.model.BaseResponse;
import io.swagger.annotations.*;

@ApiModel(value = "LoginModel", description="Login object")
public class LoginModel {

    @ApiModelProperty(value = "User Name", example = "admin", required=true)
    private String  username;

    @ApiModelProperty(value = "Password", example = "admin",required=true)
    private String  password;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    @ApiModel(value = "LoginResponseModel", description="Login response object")
    public static class LoginResponse extends BaseResponse {

        private UserOutputModel data;

        public LoginResponse(){}
        public LoginResponse(UserOutputModel user) {
            setMsgType(MessageTypeEnum.SUCCESS);
            this.data = user;
        }

        public UserOutputModel getData() { return data; }
        public void setData(UserOutputModel data) { this.data = data; }
    }
}