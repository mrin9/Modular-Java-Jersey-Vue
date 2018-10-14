package com.app.model.cart;

import com.app.model.PageResponse;
import org.hibernate.annotations.Immutable;
import javax.persistence.*;
import java.util.List;

@Entity
@Immutable //Indicates its a View not a table (cannot be updated)
@IdClass(CartModel.CartPrimaryKeys.class)
@Table(name = "cart_view")
public class CartViewModel {

    @Id @Column(name = "user_id")    private String userId;
    @Id @Column(name = "product_id") private Integer productId;
    @Column(name = "product_code")   private String productCode;
    @Column(name = "product_name")   private String productName;
    @Column(name = "description")    private String description;
    @Column(name = "quantity")       private Long quantity;
    @Column(name = "standard_cost")  private Long standardCost;
    @Column(name = "list_price")     private Long listPrice;

    public CartViewModel(){}

    public CartViewModel(Integer productId, String productCode, String productName, String description, Long standardCost, Long listPrice) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.description = description;
        this.standardCost = standardCost;
        this.listPrice = listPrice;
    }

    public String getUserId() {return userId;}
    public void setUserId(String userId) { this.userId = userId; }

    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getQuantity() { return quantity; }
    public void setQuantity(Long quantity) { this.quantity = quantity; }

    public Long getStandardCost() { return standardCost; }
    public void setStandardCost(Long standardCost) { this.standardCost = standardCost; }

    public Long getListPrice() { return listPrice; }
    public void setListPrice(Long listPrice) { this.listPrice = listPrice; }


    //Response Class for API
    public static class CartViewResponse extends PageResponse {

        private List<CartViewModel> list;

        public List<CartViewModel> getList() {return list; }
        public void setList(List<CartViewModel> list) { this.list = list; }
    }
}
