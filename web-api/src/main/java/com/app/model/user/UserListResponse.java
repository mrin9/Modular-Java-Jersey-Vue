package com.app.model.user;

import com.app.model.PageResponse;

import java.util.List;

public class UserListResponse extends PageResponse {

    private List<UserOutputModel> list = null;

    // ===== Getters & Setters =====
    public List<UserOutputModel> getList() {return list;}
    public void setList(List<UserOutputModel> list) {this.list = list;}

}
