package com.app.model.employee;

import com.app.model.PageResponse;
import java.util.List;

public class EmployeeResponse extends PageResponse {

    private List<EmployeeModel> list;

    public List<EmployeeModel> getList() {return list; }
    public void setList(List<EmployeeModel> list) { this.list = list; }


}
