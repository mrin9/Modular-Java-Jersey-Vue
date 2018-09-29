package com.app.model.cart;

import com.app.model.PageResponse;

import java.util.List;

public class CartViewResponse extends PageResponse{

    private List<CartViewModel> list;

    public List<CartViewModel> getList() {return list; }
    public void setList(List<CartViewModel> list) { this.list = list; }
}
