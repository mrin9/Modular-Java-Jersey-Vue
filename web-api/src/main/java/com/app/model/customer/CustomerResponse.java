package com.app.model.customer;

import com.app.model.PageResponse;
import java.util.List;

public class CustomerResponse extends PageResponse{

    private List<CustomerModel> list;

    public List<CustomerModel> getList() {return list; }
    public void setList(List<CustomerModel> list) { this.list = list; }
}
