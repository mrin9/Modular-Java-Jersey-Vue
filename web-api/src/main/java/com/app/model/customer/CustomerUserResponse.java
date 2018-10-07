package com.app.model.customer;

import com.app.model.PageResponse;

import java.util.List;

public class CustomerUserResponse extends PageResponse{

    private List<CustomerUserModel> list;

    public List<CustomerUserModel> getList() {return list; }
    public void setList(List<CustomerUserModel> list) { this.list = list; }
}
