package com.app.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "order_items")
@IdClass(OrderItemModel.OrderItemPrimaryKeys.class)
public class OrderItemModel {

    @Id @Column(name = "order_id")      private Integer    orderId;
    @Id @Column(name = "product_id")    private Integer    productId;
    @Column(name = "quantity")          private BigDecimal quantity;
    @Column(name = "unit_price")        private BigDecimal unitPrice;
    @Column(name = "discount")          private BigDecimal discount;
    @Column(name = "order_item_status") private String     orderItemStatus;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "date_allocated")    private Date       dateAllocated;

    // Getters and Setters
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public String getOrderItemStatus() { return orderItemStatus; }
    public void setOrderItemStatus(String orderItemStatus) { this.orderItemStatus = orderItemStatus; }

    public Date getDateAllocated() { return dateAllocated; }
    public void setDateAllocated(Date dateAllocated) { this.dateAllocated = dateAllocated; }

    static class OrderItemPrimaryKeys implements Serializable {
        private Integer    orderId;
        private Integer    productId;

        public Integer getOrderId() { return orderId; }
        public void setOrderId(Integer orderId) { this.orderId = orderId; }

        public Integer getProductId() { return productId; }
        public void setProductId(Integer productId) { this.productId = productId; }
    }
}

