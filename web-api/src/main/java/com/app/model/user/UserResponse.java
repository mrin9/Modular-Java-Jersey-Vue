package com.app.model.user;

import com.app.model.BaseResponse;
import com.app.model.PageResponse;

import java.util.List;

public class UserResponse extends BaseResponse {

    private UserOutputModel data = null;

    // ===== Getters & Setters =====
    public UserOutputModel getData() {return data;}
    public void setData(UserOutputModel data) {this.data = data;}

}
