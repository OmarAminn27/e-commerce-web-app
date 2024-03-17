package com.gov.iti.business.dtos;

import com.gov.iti.business.entities.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private Integer id;
    private String productName;
    private byte[] productImage; // New field for the image
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private String category;

    public ProductDTO(Integer id, String productName, byte[] productImage, Integer quantity, String description, BigDecimal price, String category) {
        this.id = id;
        this.productName = productName;
        this.productImage = productImage;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public ProductDTO(Product productEntity) {
        this.id = productEntity.getId();
        this.productName = productEntity.getProductName();
        this.productImage = productEntity.getProductImage(); // Assigning image field
        this.quantity = productEntity.getQuantity();
        this.description = productEntity.getDescription();
        this.price = productEntity.getPrice();
        this.category = productEntity.getCategory();
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}
