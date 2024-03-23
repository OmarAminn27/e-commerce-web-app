package com.gov.iti.business.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
public class CartItem {
    @EmbeddedId
//    @AttributeOverrides( {
//    @AttributeOverride(name="cart_id", column=@Column(name="cart_id", nullable=false) ),
//    @AttributeOverride(name="product_id", column=@Column(name="product_id", nullable=false) ) } )
    private CartItemId id;

    @MapsId("cartId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price")
    private Double totalPrice;

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", cart=" + cart +
                ", product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }


}