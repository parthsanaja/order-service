package org.parths.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.parths.orderservice.dto.OrderStatus;

@Data
@ToString
@Entity
@Table(name = "orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue
    private Integer id;

    private String productId;

    private Integer userId;

    private Double productPrice;

    private Integer qty;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
