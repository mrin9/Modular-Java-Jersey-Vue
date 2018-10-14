package com.app.model.order;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;

import com.app.model.PageResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "orders")
public class OrderModel {

    @Id @GeneratedValue private Integer id;
    @Column(name = "customer_id")   private Integer customerId;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "order_date")    private Date    orderDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "shipped_date")  private Date    shippedDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "paid_date")     private Date    paidDate;

    @Column(name = "ship_name")     private String  shipName;
    @Column(name = "ship_address1") private String  shipAddress1;
    @Column(name = "ship_address2") private String  shipAddress2;
    @Column(name = "ship_city")     private String  shipCity;
    @Column(name = "ship_state")    private String  shipState;
    @Column(name = "ship_postal_code") private String  shipPostalCode;
    @Column(name = "ship_country")  private String  shipCountry;
    @Column(name = "shipping_fee")  private BigDecimal shippingFee;
    @Column(name = "payment_type") @ApiModelProperty(allowableValues = "Check, Cash, Card") private String paymentType;
    @Column(name = "order_status") @ApiModelProperty(allowableValues = "On Hold, Shipped, Complete, New")private String orderStatus;

    //Constructors
    public OrderModel(){}
    public OrderModel(Integer id, Integer customerId  , Date   orderDate   , String orderStatus,
                      Date        shippedDate , String  shipName    , String  shipAddress1, String shipAddress2, String shipCity   , String shipState, String shipPostalCode, String shipCountry,
                      BigDecimal  shippingFee , String  paymentType , Date    paidDate
    ){
        this.id=id;
        this.customerId  = customerId ;
        this.orderDate   = orderDate;
        this.orderStatus = orderStatus;
        this.shippedDate = shippedDate;
        this.shipName    = shipName;
        this.shipAddress1= shipAddress1;
        this.shipAddress2= shipAddress2;
        this.shipCity    = shipCity;
        this.shipState   = shipState;
        this.shipPostalCode= shipPostalCode;
        this.shipCountry = shipCountry;
        this.shippingFee = shippingFee;
        this.paymentType = paymentType;
        this.paidDate    = paidDate;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public Date getShippedDate() { return shippedDate; }
    public void setShippedDate(Date shippedDate) { this.shippedDate = shippedDate; }

    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }

    public String getShipName() { return shipName; }
    public void setShipName(String shipName) { this.shipName = shipName; }

    public String getShipAddress1() { return shipAddress1; }
    public void setShipAddress1(String shipAddress1) { this.shipAddress1 = shipAddress1; }

    public String getShipAddress2() { return shipAddress2; }
    public void setShipAddress2(String shipAddress2) { this.shipAddress2 = shipAddress2; }

    public String getShipCity() { return shipCity; }
    public void setShipCity(String shipCity) { this.shipCity = shipCity; }

    public String getShipState() { return shipState; }
    public void setShipState(String shipState) { this.shipState = shipState; }

    public String getShipPostalCode() { return shipPostalCode; }
    public void setShipPostalCode(String shipPostalCode) { this.shipPostalCode = shipPostalCode; }

    public String getShipCountry() { return shipCountry; }
    public void setShipCountry(String shipCountry) { this.shipCountry = shipCountry; }

    public BigDecimal getShippingFee() {return shippingFee; }
    public void setShippingFee(BigDecimal shippingFee) {this.shippingFee = shippingFee; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType;}

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public static class OrderResponse extends PageResponse {
        private List<OrderModel> list;

        public List<OrderModel> getList() {return list; }
        public void setList(List<OrderModel> list) { this.list = list; }


    }
}
