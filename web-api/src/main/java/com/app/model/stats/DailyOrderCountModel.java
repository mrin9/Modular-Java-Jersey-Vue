package com.app.model.stats;

import com.app.model.BaseResponse;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class DailyOrderCountModel {

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    private BigInteger count;


    //Constructor
    public DailyOrderCountModel(Date date, BigInteger count) {
        this.date = date;
        this.count = count;
    }

    //Getters and Setters
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public BigInteger getCount() { return count; }
    public void setCount(BigInteger count) { this.count = count; }


    //Response Class
    public static class DailyOrderCountResponse  extends BaseResponse {
        private List<DailyOrderCountModel> list;

        public List<DailyOrderCountModel> getList() {return list; }
        public void setList(List<DailyOrderCountModel> list) { this.list = list; }
    }

}
