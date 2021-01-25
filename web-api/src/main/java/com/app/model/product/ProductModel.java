package com.app.model.product;

import javax.persistence.*;
import com.app.model.PageResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false) private Integer id;

    @Schema(example="601")
    @Column(name = "product_code")       private String  productCode;
    @Column(name = "product_name")      private String  productName;
    @Column(name = "description")       private String  description;
    @Column(name = "standard_cost")     private BigDecimal standardCost;
    @Column(name = "list_price")        private BigDecimal listPrice;
    @Column(name = "target_level")      private Integer targetLevel;
    @Column(name = "reorder_level")     private Integer reorderLevel;
    private Integer discontinued;

    @Schema(allowableValues =  {"Camera", "Laptop", "Tablet", "Phone"}, example="Camera")
    private String category;

    //Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getStandardCost() { return standardCost; }
    public void setStandardCost(BigDecimal standardCost) { this.standardCost = standardCost; }

    public BigDecimal getListPrice() { return listPrice; }
    public void setListPrice(BigDecimal listPrice) { this.listPrice = listPrice; }

    public Integer getTargetLevel() { return targetLevel; }
    public void setTargetLevel(Integer targetLevel) { this.targetLevel = targetLevel; }

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }

    public Integer getDiscontinued() { return discontinued; }
    public void setDiscontinued(Integer discontinued) { this.discontinued = discontinued; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public static class ProductResponse extends PageResponse {
        private List<ProductModel> list;

        public List<ProductModel> getList() {return list; }
        public void setList(List<ProductModel> list) { this.list = list; }
    }
}
