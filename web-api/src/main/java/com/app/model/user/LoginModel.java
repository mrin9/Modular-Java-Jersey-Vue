package com.app.model.user;

import com.app.model.BaseResponse;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginModel" , description = "Login object")
public class LoginModel {
    @Schema(description = "User Name", example = "admin", required=true)
    private String  username;

    @Schema(description = "Password", example = "password", required=true)
    private String  password;

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    @Schema(name = "LoginResponseModel" , description = "Login response object")
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