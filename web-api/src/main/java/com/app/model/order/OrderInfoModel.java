package com.app.model.order;

import com.app.model.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.annotations.Immutable;
import java.util.*;
import java.math.*;
import javax.persistence.*;

@Entity
@Immutable //Indicates its a View not a table (cannot be updated)
@Table(name = "order_info")
public class OrderInfoModel {
    @Id
    @Column(name = "order_id")      private Integer orderId;
    @Column(name = "customer_id")   private Integer customerId;
    @Column(name = "order_date")    private Date    orderDate;
    @Column(name = "shipped_date")  private Date    shippedDate;
    @Column(name = "paid_date")     private Date    paidDate;
    @Column(name = "ship_name")     private String  shipName;
    @Column(name = "ship_address1") private String  shipAddress1;
    @Column(name = "ship_address2") private String  shipAddress2;
    @Column(name = "ship_city")     private String  shipCity;
    @Column(name = "ship_state")    private String  shipState;
    @Column(name = "ship_postal_code") private String  shipPostalCode;
    @Column(name = "ship_country")  private String  shipCountry;
    @Column(name = "shipping_fee")  private BigDecimal shippingFee;

    @Schema(allowableValues =  {"Check","Cash","Card"})
    @Column(name = "payment_type") private String paymentType;

    @Schema(allowableValues =  {"On Hold", "Shipped", "Complete", "New"})
    @Column(name = "order_status") private String orderStatus;

    @Column(name = "customer_name")    private String customerName;
    @Column(name = "customer_phone")   private String customerPhone;
    @Column(name = "customer_email")   private String customerEmail;
    @Column(name = "customer_company") private String customerCompany;

    public OrderInfoModel(){}


    //Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

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

    public BigDecimal getShippingFee() { return shippingFee; }
    public void setShippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerCompany() { return customerCompany; }
    public void setCustomerCompany(String customerCompany) { this.customerCompany = customerCompany; }

    //API Response class
    public static class OrderInfoResponse extends PageResponse {
        private List<OrderInfoModel> list;

        public List<OrderInfoModel> getList() {return list; }
        public void setList(List<OrderInfoModel> list) { this.list = list; }
    }
}
