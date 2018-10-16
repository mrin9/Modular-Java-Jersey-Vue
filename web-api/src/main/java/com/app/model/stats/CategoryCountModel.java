package com.app.model.stats;

import com.app.model.BaseResponse;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class CategoryCountModel {

    private String category;
    private BigInteger count;

    //Constructor
    public CategoryCountModel(String category, BigInteger count) {
        this.category = category;
        this.count = count;
    }

    //Getters and Setters
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigInteger getCount() { return count; }
    public void setCount(BigInteger count) { this.count = count; }


    //Response Model Class used as API response
    public static class CategoryCountResponse  extends BaseResponse {
        private List<CategoryCountModel> list;

        public List<CategoryCountModel> getList() {return list; }
        public void setList(List<CategoryCountModel> list) { this.list = list; }
    }

}
