package com.shruteekatech.eCommerce.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDto {

    private Long productId;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String productName;

    @NotEmpty
    @Size(min = 2,max = 250)
    private String productDesc;

    @NotNull
    private Double productPrice;

    @NotNull
    private Integer productQuantity;

    private boolean live;

    private String imageName;

    private boolean stock;

    private CategoryDto category;


    public ProductDto() {
    }

    public ProductDto(Long productId, String productName, String productDesc, Double productPrice, Integer productQuantity, boolean live, String imageName, boolean stock, CategoryDto category) {
        this.productId = productId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.live = live;
        this.imageName = imageName;
        this.stock = stock;
        this.category = category;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                ", live=" + live +
                ", imageName='" + imageName + '\'' +
                ", stock=" + stock +
                ", category=" + category +
                '}';
    }
}
