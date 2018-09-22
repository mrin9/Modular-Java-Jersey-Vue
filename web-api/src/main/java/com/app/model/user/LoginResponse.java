package com.app.model.user;

import com.app.model.BaseResponse;
//import com.app.model.

import io.swagger.annotations.ApiModel;

@ApiModel(value = "LoginResponseModel", description="Login response object")
public class LoginResponse extends BaseResponse {

    private UserOutputModel data;

    public LoginResponse(){}
    public LoginResponse(UserOutputModel user) {
        setMsgType(MessageTypeEnum.SUCCESS);
        this.data = user;
    }

    public UserOutputModel getData() { return data; }
    public void setData(UserOutputModel data) { this.data = data; }
}
