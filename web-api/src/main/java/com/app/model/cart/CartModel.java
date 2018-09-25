package com.app.model.cart;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "cart")
@IdClass(CartModel.CartPrimaryKeys.class)
public class CartModel {
    @Id @Column(name = "user_id")    private String  userId;
    @Id @Column(name = "product_id") private Integer productId;
    @Column(name = "quantity") private BigDecimal quantity;

    //Constructors
    public CartModel(){}
    public CartModel(String userId, Integer productId, BigDecimal quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    //Getters and Setters

    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    static class CartPrimaryKeys implements Serializable {
        private String   userId;
        private Integer  productId;

        public String getUserId() {return userId;}
        public void setUserId(String userId) { this.userId = userId; }

        public Integer getProductId() { return productId; }
        public void setProductId(Integer productId) { this.productId = productId; }
    }

}
