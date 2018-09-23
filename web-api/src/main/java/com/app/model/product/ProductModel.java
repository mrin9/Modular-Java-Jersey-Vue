package com.app.model.product;

import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    private Integer id;
    private String  productCode;
    private String  productName;
    private String  description;
    private Long    standardCost;
    private Long    listPrice;
    private Integer targetLevel;
    private Integer reorderLevel;
    private Integer minimumReorderQuantity;
    private String  quantityPerUnit;
    private Integer discontinued;
    @ApiModelProperty(allowableValues = "Camera, Laptop, Tablet, Phone") private String category;

    //Getters and Setters

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getStandardCost() { return standardCost; }
    public void setStandardCost(Long standardCost) { this.standardCost = standardCost; }

    public Long getListPrice() { return listPrice; }
    public void setListPrice(Long listPrice) { this.listPrice = listPrice; }

    public Integer getTargetLevel() { return targetLevel; }
    public void setTargetLevel(Integer targetLevel) { this.targetLevel = targetLevel; }

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }

    public Integer getMinimumReorderQuantity() { return minimumReorderQuantity; }
    public void setMinimumReorderQuantity(Integer minimumReorderQuantity) { this.minimumReorderQuantity = minimumReorderQuantity; }

    public String getQuantityPerUnit() { return quantityPerUnit; }
    public void setQuantityPerUnit(String quantityPerUnit) { this.quantityPerUnit = quantityPerUnit; }

    public Integer getDiscontinued() { return discontinued; }
    public void setDiscontinued(Integer discontinued) { this.discontinued = discontinued; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
