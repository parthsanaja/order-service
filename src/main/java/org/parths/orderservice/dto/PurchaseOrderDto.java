package org.parths.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderDto {

    private Integer id;

    private String productId;

    private Integer userId;

    private Double productPrice;

    private Integer qty;

    private Double amount;

    private OrderStatus status;
}
