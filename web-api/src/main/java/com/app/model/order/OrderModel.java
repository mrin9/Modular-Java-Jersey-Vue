package com.app.model.order;

import java.math.BigDecimal;
import java.util.*;
import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "orders")
public class OrderModel {

    @Id
    private Integer id;
    private Integer employeeId;
    private Integer customerId;
    private Date    orderDate;
    private Date    shippedDate;
    private Date    paidDate;
    private String  shipName;
    private String  shipAddress1;
    private String  shipAddress2;
    private String  shipCity;
    private String  shipState;
    private String  shipPostalCode;
    private String  shipCountry;
    private BigDecimal shippingFee;
    @ApiModelProperty(allowableValues = "Check, Cash, Card") private String paymentType;
    @ApiModelProperty(allowableValues = "On Hold, Shipped, Complete, New")private String orderStatus;

    //Constructors
    public OrderModel(){}
    public OrderModel(Integer id, Integer employeeId  , Integer customerId  , Date   orderDate   , String orderStatus,
         Date       shippedDate , String  shipName    , String  shipAddress1, String shipAddress2, String shipCity   , String shipState, String shipPostalCode, String shipCountry,
         BigDecimal shippingFee , String  paymentType , Date    paidDate
    ){
        this.id=id;
        this.employeeId  = employeeId ;
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

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

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
}
