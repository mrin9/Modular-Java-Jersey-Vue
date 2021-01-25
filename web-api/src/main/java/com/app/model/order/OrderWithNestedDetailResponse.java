package com.app.model.order;

import com.app.model.PageResponse;
import java.util.*;

public class OrderWithNestedDetailResponse  extends PageResponse {
    private List<OrderWithNestedDetailModel> list = new ArrayList<>();

    public List<OrderWithNestedDetailModel> getList() { return list; }
    public void setList(List<OrderWithNestedDetailModel> list) { this.list = list; }
}
