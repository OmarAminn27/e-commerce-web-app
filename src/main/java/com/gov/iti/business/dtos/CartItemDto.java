package com.gov.iti.business.dtos;

import com.gov.iti.business.entities.Cart;
import com.gov.iti.business.entities.CartItem;
import com.gov.iti.business.entities.CartItemId;
import com.gov.iti.business.entities.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;


@Value
@Getter
@Setter
public class CartItemDto implements Serializable {
    Integer cartId;
    ProductDTO productDTO;
    Integer quantity;
    Double totalPrice;

    public CartItemDto(Integer cartId, ProductDTO productDTO, Integer quantity, Double totalPrice) {
        this.cartId = cartId;
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public CartItemDto(CartItem cartItemEntity) {
        this.cartId = cartItemEntity.getId().getCartId();
        this.productDTO = new ProductDTO(cartItemEntity.getProduct());
        this.quantity = cartItemEntity.getQuantity();
        this.totalPrice = cartItemEntity.getTotalPrice();
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "cartId='" + cartId.toString() + '\'' +
                ", product='" + productDTO.getId() + '\'' +
                ", quantity='" + quantity.toString() + '\'' +
                ", totalPrice=" + totalPrice.toString() +
                '}';
    }
}