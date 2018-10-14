package com.app.model.stats;

import com.app.model.BaseResponse;
import com.app.model.cart.CartModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Immutable //Indicates its a View not a table (cannot be updated)
public class DailySaleModel {

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date date;
    private BigDecimal amount;

    //Constructor
    public DailySaleModel(Date date, BigDecimal amount) {
        this.date = date;
        this.amount = amount;
    }

    //Getters and Setters
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public static class DailySaleResponse  extends BaseResponse {
        private List<DailySaleModel> list;

        public List<DailySaleModel> getList() {return list; }
        public void setList(List<DailySaleModel> list) { this.list = list; }
    }

}
