package com.app.model.employee;

import com.app.model.PageResponse;

import java.util.List;

public class EmployeeUserResponse extends PageResponse {

    private List<EmployeeUserModel> list;

    public List<EmployeeUserModel> getList() {return list; }
    public void setList(List<EmployeeUserModel> list) { this.list = list; }


}
