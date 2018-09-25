package com.app.model.cart;

import com.app.model.PageResponse;
import com.app.model.customer.CustomerModel;

import java.util.List;

public class CartResponse extends PageResponse{

    private List<CartModel> list;

    public List<CartModel> getList() {return list; }
    public void setList(List<CartModel> list) { this.list = list; }
}
