package com.app.model.order;

import com.app.model.PageResponse;
import java.util.List;

public class OrderInfoResponse extends PageResponse {
    private List<OrderInfoModel> list;

    public List<OrderInfoModel> getList() {return list; }
    public void setList(List<OrderInfoModel> list) { this.list = list; }


}
