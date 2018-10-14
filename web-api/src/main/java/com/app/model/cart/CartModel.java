package com.app.model.cart;

import com.app.model.PageResponse;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "cart")
@IdClass(CartModel.CartPrimaryKeys.class)
public class CartModel {
    @Id @Column(name = "user_id")    private String  userId;
    @Id @Column(name = "product_id") private Integer productId;
    @Column(name = "quantity") private Long quantity;

    //Constructors
    public CartModel(){}
    public CartModel(String userId, Integer productId, Long quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    //Getters and Setters

    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    static class CartPrimaryKeys implements Serializable {
        private String   userId;
        private Integer  productId;

        public String getUserId() {return userId;}
        public void setUserId(String userId) { this.userId = userId; }

        public Integer getProductId() { return productId; }
        public void setProductId(Integer productId) { this.productId = productId; }
    }


    //Response for CartModel
    public static class CartResponse extends PageResponse {

        private List<CartModel> list;

        public List<CartModel> getList() {return list; }
        public void setList(List<CartModel> list) { this.list = list; }
    }
}
