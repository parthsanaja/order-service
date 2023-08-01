package org.parths.orderservice.controller;

import org.parths.orderservice.dto.PurchaseOrderDto;
import org.parths.orderservice.service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public Mono<PurchaseOrderDto> order(@RequestBody Mono<PurchaseOrderDto> purchaseOrderDtoMono){
        return purchaseOrderService.createPurchaseOrder(purchaseOrderDtoMono);
    }


}
