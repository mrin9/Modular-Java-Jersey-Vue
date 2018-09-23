package com.app.model.order;

import com.app.model.PageResponse;
import java.util.List;

public class OrderResponse extends PageResponse{
    private List<OrderModel> list;

    public List<OrderModel> getList() {return list; }
    public void setList(List<OrderModel> list) { this.list = list; }


}
