package org.parths.orderservice.mapper;

import org.mapstruct.Mapper;
import org.parths.orderservice.dto.PurchaseOrderDto;
import org.parths.orderservice.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

@Component
public class PurchaseOrderMapper {

    public PurchaseOrderDto purchaseOrderToDto(PurchaseOrder purchaseOrder) {
        PurchaseOrderDto purchaseOrderDto = new PurchaseOrderDto();
        purchaseOrderDto.setId(purchaseOrder.getId());
        purchaseOrderDto.setUserId(purchaseOrder.getUserId());
        purchaseOrderDto.setProductId(purchaseOrder.getProductId());
        purchaseOrderDto.setProductPrice(purchaseOrder.getProductPrice());
        purchaseOrderDto.setQty(purchaseOrder.getQty());
        purchaseOrderDto.setAmount(purchaseOrder.getAmount());
        purchaseOrderDto.setStatus(purchaseOrder.getStatus());
        return purchaseOrderDto;
    }

    public PurchaseOrder dtoToPurchaseOrder(PurchaseOrderDto purchaseOrderDto) {
        PurchaseOrder order = new PurchaseOrder();
        order.setId(purchaseOrderDto.getId());
        order.setUserId(purchaseOrderDto.getUserId());
        order.setProductId(purchaseOrderDto.getProductId());
        order.setProductPrice(purchaseOrderDto.getProductPrice());
        order.setQty(purchaseOrderDto.getQty());
        order.setAmount(purchaseOrderDto.getAmount());
        order.setStatus(purchaseOrderDto.getStatus());
        return order;
    }
}
