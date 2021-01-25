package com.app.model.stats;

import com.app.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.*;

public class DailySaleModel {
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    private BigDecimal saleAmount;
    private BigDecimal discount;

    //Constructor
    public DailySaleModel(Date date, BigDecimal saleAmount, BigDecimal discount) {
        this.date = date;
        this.saleAmount = saleAmount;
        this.discount = discount;
    }

    //Getters and Setters
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public BigDecimal getSaleAmount() { return saleAmount; }
    public void setSaleAmount(BigDecimal saleAmount) { this.saleAmount = saleAmount; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    //Response Class
    public static class DailySaleResponse  extends BaseResponse {
        private List<DailySaleModel> list;

        public List<DailySaleModel> getList() {return list; }
        public void setList(List<DailySaleModel> list) { this.list = list; }
    }
}
