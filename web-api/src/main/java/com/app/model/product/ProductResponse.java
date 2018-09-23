package com.app.model.product;

import com.app.model.PageResponse;
import java.util.List;

public class ProductResponse extends PageResponse{
    private List<ProductModel> list;

    public List<ProductModel> getList() {return list; }
    public void setList(List<ProductModel> list) { this.list = list; }


}
