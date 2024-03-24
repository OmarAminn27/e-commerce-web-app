package com.gov.iti.business.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderDTO {
    String username;
    Integer orderID;
    LocalDate orderedAt;
    Double totalCost;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "username='" + username + '\'' +
                ", orderID=" + orderID +
                ", orderedAt=" + orderedAt +
                ", totalPrice=" + totalCost +
                '}';
    }
}
